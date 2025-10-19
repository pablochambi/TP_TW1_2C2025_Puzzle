package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.Partida;
import com.tallerwebi.dominio.PartidaDTO;
import com.tallerwebi.dominio.ServicioLogin;
import com.tallerwebi.dominio.ServicioPartida;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
public class ControladorHistorial {

    private ServicioPartida servicioPartida;
    private ServicioLogin servicioLogin;

    @Autowired
    public ControladorHistorial(ServicioPartida servicioPartida, ServicioLogin servicioLogin) {
        this.servicioPartida = servicioPartida;
        this.servicioLogin = servicioLogin;
    }

    @RequestMapping("/historial")
    public ModelAndView historial(HttpServletRequest request) {

        Long idUsuario = (Long) request.getSession().getAttribute("id_usuario");

        if (idUsuario == null) {
            return new ModelAndView("redirect:/login");
        }

        List<PartidaDTO> listaPartidas = servicioPartida.obtenerTodasLasPartidasDTO(idUsuario);
        ModelMap model = new ModelMap();
        model.put("partidas", listaPartidas);
        model.put("usuario",servicioLogin.consultarUsuarioDTOPorId(idUsuario));

        return new ModelAndView("historial", model);
    }
}
