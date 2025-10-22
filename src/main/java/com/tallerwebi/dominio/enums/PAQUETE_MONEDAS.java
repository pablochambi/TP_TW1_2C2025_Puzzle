package com.tallerwebi.dominio.enums;




public enum PAQUETE_MONEDAS {

    PAQUETE_1(1, 1000, 1),
    PAQUETE_2(2, 2200, 2),
    PAQUETE_3(3, 7000, 6),
    PAQUETE_4(4, 11000, 9),
    PAQUETE_5(5, 25000, 20),
    PAQUETE_6(6, 150000, 100);


    private Integer id;
    private Integer cantidadMonedas;
    private Integer precioARS;

    PAQUETE_MONEDAS(Integer id, Integer cantidadMonedas, Integer precioARS) {
        this.id = id;
        this.cantidadMonedas = cantidadMonedas;
        this.precioARS = precioARS;
    }

   PAQUETE_MONEDAS() {

    }

    public static PAQUETE_MONEDAS getPorId(Integer id) {
        for (PAQUETE_MONEDAS paquete : values()) {
            if (paquete.id.equals(id)) {
                return paquete;
            }
        }
        return null;
    }


    public Integer getId() { return id; }
    public Integer getCantidadMonedas() { return cantidadMonedas; }
    public Integer getPrecioARS() { return precioARS; }
}
