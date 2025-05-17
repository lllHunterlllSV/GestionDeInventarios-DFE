package com.gfu.gestioninventario.Controller;

import com.gfu.gestioninventario.Models.Cliente;
import com.gfu.gestioninventario.Models.TipoCliente;
import com.gfu.gestioninventario.Services.ClienteService;
import com.gfu.gestioninventario.Services.TipoClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import jakarta.validation.Valid;
import org.springframework.validation.BindingResult;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/clientes")
public class ClienteController {

    private final ClienteService clienteService;
    private final TipoClienteService tipoClienteService;

    @Autowired
    public ClienteController(ClienteService clienteService, TipoClienteService tipoClienteService) {
        this.clienteService = clienteService;
        this.tipoClienteService = tipoClienteService;
    }

    // Muestra la lista de clientes con opciones de busqueda/filtrado
    @GetMapping("/lista")
    public String listarClientes(
            @RequestParam(value = "nombre", required = false) String nombre,
            @RequestParam(value = "tipoClienteId", required = false) Integer tipoClienteId,
            @RequestParam(value = "estado", required = false) Boolean estado,
            Model model
    ) {
        System.out.println("DEBUG: Entrando a listarClientes()"); // Log de depuracion
        List<Cliente> clientes;

        // Usa el metodo de busqueda del servicio si hay algun parametro de filtro
        if ((nombre != null && !nombre.trim().isEmpty()) || tipoClienteId != null || estado != null) {
             System.out.println("DEBUG: Buscando clientes con filtros: nombre=" + nombre + ", tipoClienteId=" + tipoClienteId + ", estado=" + estado); // Log de depuracion
            clientes = clienteService.buscarClientes(nombre, tipoClienteId, estado);
        } else {
             System.out.println("DEBUG: Listando todos los clientes."); // Log de depuracion
            clientes = clienteService.findAll();
        }

        model.addAttribute("clientes", clientes);
        model.addAttribute("title", "Gestion de Clientes");
        model.addAttribute("nombre", nombre); // Para mantener el valor en el campo de busqueda
        model.addAttribute("tipoClienteId", tipoClienteId); // Para mantener el valor en el select de tipo cliente
        model.addAttribute("estado", estado); // Para mantener el valor en el select de estado

        // Carga los tipos de cliente para el filtro en la vista
        List<TipoCliente> tiposCliente = tipoClienteService.findAll();
        model.addAttribute("tiposCliente", tiposCliente);
        System.out.println("DEBUG: Cargados " + tiposCliente.size() + " tipos de cliente para la vista."); // Log de depuracion


        System.out.println("DEBUG: Saliendo de listarClientes(). Clientes encontrados: " + clientes.size()); // Log de depuracion
        return "gestionClientes"; // Nombre del archivo HTML de la lista de clientes
    }

    // Muestra el formulario para crear un nuevo cliente
    @GetMapping("/nuevo")
    public String formularioNuevoCliente(Model model) {
         System.out.println("DEBUG: Entrando a formularioNuevoCliente()"); // Log de depuracion
        Cliente nuevoCliente = new Cliente();
        // Establece el estado por defecto a true para nuevos clientes
        nuevoCliente.setEstado(true);
        model.addAttribute("cliente", nuevoCliente); // Pasa un objeto Cliente vacio
        model.addAttribute("title", "Registrar Cliente");
        model.addAttribute("isEdit", false); // Indica que es un formulario de creacion

        // Carga los tipos de cliente para el select en el formulario
        List<TipoCliente> tiposCliente = tipoClienteService.findAll();
        model.addAttribute("tiposCliente", tiposCliente);
        System.out.println("DEBUG: Cargados " + tiposCliente.size() + " tipos de cliente para el formulario de nuevo cliente."); // Log de depuracion


         System.out.println("DEBUG: Saliendo de formularioNuevoCliente()"); // Log de depuracion
        return "formularioCliente"; 
    }

    // Muestra el formulario para editar un cliente existente
    @GetMapping("/editar/{id}")
    public String formularioEditarCliente(@PathVariable Integer id, Model model, RedirectAttributes redirectAttributes) {
         System.out.println("DEBUG: Entrando a formularioEditarCliente() para ID: " + id); // Log de depuracion
        Optional<Cliente> clienteExistente = clienteService.findById(id);

        if (clienteExistente.isPresent()) {
             System.out.println("DEBUG: Cliente encontrado: " + clienteExistente.get().getNombreCliente()); // Log de depuracion
            model.addAttribute("cliente", clienteExistente.get());
            model.addAttribute("title", "Editar Cliente");
            model.addAttribute("isEdit", true); // Indica que es un formulario de edicion

            // Carga los tipos de cliente para el select en el formulario
            List<TipoCliente> tiposCliente = tipoClienteService.findAll();
            model.addAttribute("tiposCliente", tiposCliente);
            System.out.println("DEBUG: Cargados " + tiposCliente.size() + " tipos de cliente para el formulario de editar cliente."); // Log de depuracion


             System.out.println("DEBUG: Saliendo de formularioEditarCliente() - Mostrando formulario."); // Log de depuracion
            return "formularioCliente"; 
        } else {
             System.out.println("DEBUG: Cliente con ID " + id + " no encontrado para edicion."); // Log de depuracion
            redirectAttributes.addFlashAttribute("error", "Cliente no encontrado para edicion.");
             System.out.println("DEBUG: Redirigiendo a /clientes/lista"); // Log de depuracion
            return "redirect:/clientes/lista"; // Redirige a la lista con un mensaje de error
        }
    }

    // Guarda un nuevo cliente o actualiza uno existente
    @PostMapping("/guardar")
    public String guardarCliente(
            @Valid @ModelAttribute Cliente cliente, 
            BindingResult bindingResult,
            Model model,
            RedirectAttributes redirectAttributes,
             // Capturamos el parametro 'estado' manualmente. Sera 'on' si marcado, null si desmarcado.
            @RequestParam(value = "estado", required = false) String estadoParam,
             // Capturamos el tipoClienteId manualmente.
            @RequestParam(value = "tipoCliente.tipoClienteId", required = false) Integer tipoClienteIdParam
    ) {
         System.out.println("DEBUG: Entrando a guardarCliente()"); // Log de depuracion
         System.out.println("DEBUG: Cliente bindeado: " + cliente); // Log de depuracion
         System.out.println("DEBUG: estadoParam recibido: " + estadoParam); // Log de depuracion
         System.out.println("DEBUG: tipoClienteIdParam recibido: " + tipoClienteIdParam); // Log de depuracion

        if (cliente.getClienteId() != null) { 
             cliente.setEstado("on".equals(estadoParam)); 
             System.out.println("DEBUG: Es edicion. Estado del cliente ajustado a: " + cliente.getEstado()); // Log de depuracion
        } else { // Es creacion
             cliente.setEstado(true); // Los nuevos clientes siempre inician activos por defecto
             System.out.println("DEBUG: Es creacion. Estado del cliente ajustado a: " + cliente.getEstado()); // Log de depuracion
        }

        if (tipoClienteIdParam != null) {
            System.out.println("DEBUG: tipoClienteIdParam es " + tipoClienteIdParam + ". Buscando TipoCliente..."); // Log de depuracion
            Optional<TipoCliente> tipoClienteOptional = tipoClienteService.findById(tipoClienteIdParam);
            if (tipoClienteOptional.isPresent()) {
                 System.out.println("DEBUG: TipoCliente encontrado: " + tipoClienteOptional.get().getTipoCliente()); // Log de depuracion
                cliente.setTipoCliente(tipoClienteOptional.get());
            } else {
                 System.out.println("DEBUG: TipoCliente con ID " + tipoClienteIdParam + " no encontrado."); // Log de depuracion
                 model.addAttribute("cliente", cliente);
                 model.addAttribute("title", cliente.getClienteId() == null ? "Registrar Cliente" : "Editar Cliente");
                 model.addAttribute("isEdit", cliente.getClienteId() != null);
                 model.addAttribute("tiposCliente", tipoClienteService.findAll()); // Recargar tipos
                 model.addAttribute("error", "Tipo de cliente seleccionado no valido.");
                 System.out.println("DEBUG: Saliendo de guardarCliente() con error de TipoCliente."); // Log de depuracion
                 return "formularioCliente";
            }
        } else {
              System.out.println("DEBUG: tipoClienteIdParam es null. Tipo de cliente no seleccionado."); // Log de depuracion
              model.addAttribute("cliente", cliente);
              model.addAttribute("title", cliente.getClienteId() == null ? "Registrar Cliente" : "Editar Cliente");
              model.addAttribute("isEdit", cliente.getClienteId() != null);
              model.addAttribute("tiposCliente", tipoClienteService.findAll()); // Recargar tipos
              model.addAttribute("error", "Debe seleccionar un tipo de cliente.");
              System.out.println("DEBUG: Saliendo de guardarCliente() con error de TipoCliente faltante."); // Log de depuracion
              return "formularioCliente";
        }

        if (bindingResult.hasErrors()) {
             System.out.println("DEBUG: Error de validacion de BindingResult."); // Log de depuracion
             bindingResult.getAllErrors().forEach(error -> System.out.println("DEBUG: Binding Error: " + error.getObjectName() + " - " + error.getDefaultMessage())); // Log de depuracion
             model.addAttribute("cliente", cliente); 
             model.addAttribute("title", cliente.getClienteId() == null ? "Registrar Cliente" : "Editar Cliente");
             model.addAttribute("isEdit", cliente.getClienteId() != null);
             model.addAttribute("tiposCliente", tipoClienteService.findAll()); 
             System.out.println("DEBUG: Saliendo de guardarCliente() con errores de validacion."); // Log de depuracion
             return "formularioCliente"; 
        }

        System.out.println("DEBUG: Validacion de BindingResult pasada. Intentando guardar cliente..."); // Log de depuracion
        try {
            clienteService.save(cliente); 
            redirectAttributes.addFlashAttribute("exito", "Cliente guardado correctamente.");
            System.out.println("DEBUG: Cliente guardado exitosamente. Redirigiendo a /clientes/lista"); // Log de depuracion
            return "redirect:/clientes/lista";
        } catch (Exception e) {
            // Maneja cualquier otra excepcion durante el guardado
            System.err.println("DEBUG: Excepcion durante el guardado del cliente: " + e.getMessage()); // Log de depuracion de error
            e.printStackTrace(); // Imprime el stack trace para mas detalles
            model.addAttribute("cliente", cliente);
            model.addAttribute("title", cliente.getClienteId() == null ? "Registrar Cliente" : "Editar Cliente");
            model.addAttribute("isEdit", cliente.getClienteId() != null);
            model.addAttribute("tiposCliente", tipoClienteService.findAll()); // Recargar tipos
            model.addAttribute("error", "Ocurrio un error al guardar el cliente: " + e.getMessage());
            System.out.println("DEBUG: Saliendo de guardarCliente() con error en el servicio."); // Log de depuracion
            return "formularioCliente";
        }
    }

    // Elimina un cliente por su ID
    @PostMapping("/eliminar/{id}")
    public String eliminarCliente(@PathVariable Integer id, RedirectAttributes redirectAttributes) {
         System.out.println("DEBUG: Entrando a eliminarCliente() para ID: " + id); // Log de depuracion
        try {
            clienteService.deleteById(id); // Elimina el cliente usando el servicio
            redirectAttributes.addFlashAttribute("exito", "Cliente eliminado correctamente.");
            System.out.println("DEBUG: Cliente con ID " + id + " eliminado exitosamente. Redirigiendo a /clientes/lista"); // Log de depuracion
        } catch (Exception e) {
            // Maneja errores si no se puede eliminar 
            System.err.println("DEBUG: Error al eliminar cliente con ID " + id + ": " + e.getMessage()); // Log de depuracion de error
            e.printStackTrace(); // Imprime el stack trace para mas detalles
            redirectAttributes.addFlashAttribute("error", "No se pudo eliminar el cliente: " + e.getMessage());
            System.out.println("DEBUG: Saliendo de eliminarCliente() con error."); // Log de depuracion
        }
        return "redirect:/clientes/lista"; // Redirige a la lista con el resultado
    }
}