package com.tallerwebi.presentacion;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
public class SudokuSocketController {

    @MessageMapping("/cell-update")
    @SendTo("/topic/board-updates")
    public CellUpdate handleCellUpdate(CellUpdate update) {
        System.out.println("📩 Celda actualizada: " + update);
        return update; // Se reenvía el mismo objeto a todos
    }

    public static class CellUpdate {
        public int row;
        public int col;
        public int value;
        public boolean correct;
        public String playerId;

        @Override
        public String toString() {
            return String.format(
                    "row=%d, col=%d, value=%d, correct=%b, playerId=%s",
                    row, col, value, correct, playerId
            );
        }
    }
}
