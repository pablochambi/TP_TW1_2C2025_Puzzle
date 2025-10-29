package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.Ranking;
import com.tallerwebi.dominio.implementacion.ServicioPartidaImpl;
import com.tallerwebi.dominio.interfaces.ServicioLogin;
import com.tallerwebi.dominio.interfaces.ServicioPartida;
import com.tallerwebi.dominio.interfaces.ServicioRanking;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
public class ControladorRanking {
    private final ServicioLogin servicioLogin;
    private final ServicioPartida servicioPartida;
    private final ServicioRanking servicioRanking;

    public ControladorRanking(ServicioLogin servicioLogin, ServicioPartida servicioPartida, ServicioRanking servicioRanking) {
        this.servicioLogin = servicioLogin;
        this.servicioPartida = servicioPartida;
        this.servicioRanking = servicioRanking;
    }



    /*
    -recibir parametros del front
      .dificultad
      .orden segun criterio
    -validar sesion con request
    -modelar datos usuario
     .nombre usuario
     .monedas
    -delegar generacion del ranking al servicio pasandole parametros
    -obtener ranking
    -pasar ranking al front
     */

    @GetMapping(path = "/ranking")
    public ModelAndView irARanking(@RequestParam(required = false, defaultValue = "GENERAL") String dificultad,
                                   @RequestParam(required = false, defaultValue = "PUNTAJE") String orden,
            HttpSession session) {
        ModelMap model = new ModelMap();

        Long idUsuario = obtenerIdUsuarioDeSesion(session);
        if (idUsuario == null) {
            return new ModelAndView("redirect:/login");
        }

        model.put("monedas", servicioLogin.obtenerMonedas(idUsuario));

        List<Ranking> ranking = servicioRanking.obtenerRanking(dificultad, orden);



        model.put("dificultadSeleccionada", dificultad);
        model.put("ordenSeleccionado", orden);
        model.put("ranking", ranking);
        model.put("usuario", servicioLogin.consultarUsuarioDTOPorId(idUsuario));
        return new ModelAndView("ranking", model);
    }




    private Long obtenerIdUsuarioDeSesion(HttpSession session) {
        return (Long) session.getAttribute("id_usuario");
    }
}
