package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.PartidaDTO;
import com.tallerwebi.dominio.interfaces.ServicioLogin;
import com.tallerwebi.dominio.interfaces.ServicioPartida;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.Collections;
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

    @GetMapping("/actualizar-orden")
    public ModelAndView actualizarOrden(
            @RequestParam(defaultValue = "antiguo") String orden,
            HttpServletRequest request) {

        Long idUsuario = (Long) request.getSession().getAttribute("id_usuario");
        if (idUsuario == null) {
            return new ModelAndView("redirect:/login");
        }
        List<PartidaDTO> partidas = Collections.emptyList();

        if(orden.equals("reciente")) {
            partidas = servicioPartida.obtenerTodasLasPartidasDTODelMasRecienteAlMasAntiguo(idUsuario);
        }else if(orden.equals("antiguo")) {
            partidas = servicioPartida.obtenerTodasLasPartidasDTO(idUsuario);
        }

        ModelMap model = new ModelMap();
        model.addAttribute("partidas", partidas);

        // 🔹 Si querés devolver el fragmento Thymeleaf (para AJAX):
        return new ModelAndView("historial :: tablaPartidas", model);

        // 🔹 O si querés devolver toda la vista principal:
        // return new ModelAndView("thymeleaf/historial", model);
    }

}
