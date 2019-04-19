package hello.controller;

import com.google.gson.Gson;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

@Controller
public class TestController {

    @RequestMapping(value = "/ttt",method = RequestMethod.GET)
    public String tt(){
        System.out.println("rrr");
        return "tt";
    }

    @RequestMapping(value = "/saveData",method = RequestMethod.POST)
    @ResponseBody
    public String saveData(@RequestBody String data,String pId,String userId) throws IOException {

        //String data=readJSONString(httpRequest);
        System.out.println(data);
        Gson gson = new Gson();
        Map map = gson.fromJson(data, Map.class);
        Map qa = (Map) map.get("qa");
        Iterator iterator = qa.keySet().iterator();
        while (iterator.hasNext()){
            System.out.println(qa.get(iterator.next()));
        }
        return "S";
    }

    private String readJSONString(HttpServletRequest request) {
        StringBuilder json = new StringBuilder();
        String line;
        try {
            BufferedReader reader = request.getReader();
            while ((line = reader.readLine()) != null) {
                json.append(line);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return json.toString();
    }
}
