package hello;

import java.text.SimpleDateFormat;
import java.util.concurrent.TimeUnit;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.stereotype.Component;

@Component
public class Runner implements CommandLineRunner {

    private final RabbitTemplate rabbitTemplate;
    private final Receiver receiver;
    private final ConfigurableApplicationContext context;

    public Runner(Receiver receiver, RabbitTemplate rabbitTemplate,
                  ConfigurableApplicationContext context) {
        this.receiver = receiver;
        this.rabbitTemplate = rabbitTemplate;
        this.context = context;
    }

    @Override
    public void run(String... args) throws Exception {
        System.out.println("Sending message..."+new SimpleDateFormat("yyyy-MM-dd"));
        rabbitTemplate.setConfirmCallback((correlationData, ack, cause) -> {
            System.out.println("回调函数，当前线程id：" + Thread.currentThread());
            //自己的处理逻辑
            System.out.println("confirm--:correlationData:" + correlationData + ",ack:" + ack + ",cause:" + cause + "message" + "messageVo");
        });
        rabbitTemplate.setReturnCallback((msg, replyCode, replyText, exchange2, routingKey2) -> {
            //路由失败回调

        });
        rabbitTemplate.convertAndSend(Application.QUEUE_NAME, "Hello from RabbitMQ!");
        receiver.getLatch().await(10000, TimeUnit.MILLISECONDS);
        context.close();
    }

}