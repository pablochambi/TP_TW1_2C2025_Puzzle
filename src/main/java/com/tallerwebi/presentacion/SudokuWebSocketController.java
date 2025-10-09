package com.tallerwebi.presentacion;

import org.dom4j.rule.Mode;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class SudokuWebSocketController {

//    @MessageMapping("/cell-update")     // recibe desde el cliente
//    @SendTo("/topic/updates")           // envía a todos los suscriptores
//    public CellUpdate handleCellUpdate(CellUpdate update) {
//        System.out.println("📩 Update recibido: " + update);
//        return update;
//    }
//
//    public static class CellUpdate {
//        public int row;
//        public int col;
//        public String value;
//        public String color;
//    }
//
//    @RequestMapping("test")
//    public ModelAndView test() {
//        return new ModelAndView("test");
//    }

}
