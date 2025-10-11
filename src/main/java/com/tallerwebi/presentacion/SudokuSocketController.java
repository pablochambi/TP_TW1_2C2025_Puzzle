package com.tallerwebi.presentacion;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
public class SudokuSocketController {

    @MessageMapping("/cell-update") // el cliente envía acá
    @SendTo("/topic/board-updates") // todos los clientes suscritos reciben
    public CellUpdate handleCellUpdate(CellUpdate update) {
        System.out.println("Celda actualizada: " + update);
        return update; // se reenvía el mismo objeto a los demás
    }

    public static class CellUpdate {
        public int row;
        public int col;
        public int value;
    }
}
