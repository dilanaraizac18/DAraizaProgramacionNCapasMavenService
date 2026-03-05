package com.digis01.DAraizaProgramacionNCapasMaven;

import com.digis01.DAraizaProgramacionNCapasMaven.Configuration.DAO.ColoniaDAOImplementation;
import com.digis01.DAraizaProgramacionNCapasMaven.Configuration.DAO.EstadoDAOImplementation;
import com.digis01.DAraizaProgramacionNCapasMaven.Configuration.DAO.MunicipioDAOImplementation;
import com.digis01.DAraizaProgramacionNCapasMaven.Configuration.DAO.PaisDAOImplementation;
import com.digis01.DAraizaProgramacionNCapasMaven.Configuration.DAO.RolDAOImplementation;
import com.digis01.DAraizaProgramacionNCapasMaven.Configuration.DAO.UsuarioDAOImplementation;
import com.digis01.DAraizaProgramacionNCapasMaven.Configuration.DAO.UsuarioDAOJPAImplementation;
import com.digis01.DAraizaProgramacionNCapasMaven.ML.Colonia;
import com.digis01.DAraizaProgramacionNCapasMaven.ML.Direccion;
import com.digis01.DAraizaProgramacionNCapasMaven.ML.ErroresArchivo;
import com.digis01.DAraizaProgramacionNCapasMaven.ML.Pais;
import com.digis01.DAraizaProgramacionNCapasMaven.ML.Result;
import com.digis01.DAraizaProgramacionNCapasMaven.ML.Rol;
import com.digis01.DAraizaProgramacionNCapasMaven.ML.Usuario;
import com.digis01.DAraizaProgramacionNCapasMaven.Service.ValidationService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.UUID;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("usuario")
public class UsuarioController {
    @Autowired
    private UsuarioDAOJPAImplementation usuarioDAOJPAImplementation;

    @Autowired
    private UsuarioDAOImplementation usuarioDAOImplementation;

    @Autowired
    private PaisDAOImplementation paisDAOImplementation;

    @Autowired
    private RolDAOImplementation rolDAOImplementation;

    @Autowired
    private EstadoDAOImplementation estadoDAOImplementation;

    @Autowired
    private MunicipioDAOImplementation municipioDAOImplementation;

    @Autowired
    private ColoniaDAOImplementation coloniaDAOImplementation;

    @Autowired
    private ValidationService validationService;

    @GetMapping
    public String Usuarios(Model model) {
//        List<Usuario> usuario= new ArrayList<>();  
//        usuario.add(new Usuario("Dilan","Araiza"));
//        
//        model.addAttribute("usuario", usuario);
//        return "GetAll";
        Result resultall = usuarioDAOJPAImplementation.GetAll();
        
        
        Result resultRol = rolDAOImplementation.GetAll();
        model.addAttribute("roles", resultRol.objects);
//        Result result = usuarioDAOImplementation.GetAll();
        model.addAttribute("usuario", resultall.objects);

        model.addAttribute("usuariobuscar", new Usuario());

        return ("GetAll");
    }

    @PostMapping
    public String Search(@ModelAttribute("usuariobuscar") Usuario usuario, Model model) {
        Result result = new Result();
        try {
            Result resultsearch = usuarioDAOImplementation.Search(usuario);
            model.addAttribute("usuarios", resultsearch.objects);
            Result resultRol = rolDAOImplementation.GetAll();
            model.addAttribute("roles", resultRol.objects);
            Result resultpais = paisDAOImplementation.GetAll();
            model.addAttribute("paises", resultpais.objects);
            model.addAttribute("usuariobuscar", usuario);

        } catch (Exception ex) {
            result.correct = false;
            result.errorMessage = ex.getLocalizedMessage();
            result.ex = ex;

        }
        return "GetAll";
    }

    @GetMapping("details")
    public String DetailsVista(Model model) {

        return ("Details");
    }

    @GetMapping("form")
    public String Formulario(Model model) {
        model.addAttribute("usuario", new Usuario());
        Result resultPaises = paisDAOImplementation.GetAll();
        model.addAttribute("paises", resultPaises.objects);

        Result resultRol = rolDAOImplementation.GetAll();
        model.addAttribute("roles", resultRol.objects);

        return "Formulario";
    }

    @PostMapping("form")
    public String Formulario(@Valid @ModelAttribute("usuario") Usuario usuario, BindingResult bindingResult, @RequestParam("imagen") MultipartFile imagen, Model model) {
        Result result = new Result();

        try {
//        if (bindingResult.hasErrors()) {
//            model.addAttribute("usuario", usuario);
//            model.addAttribute("roles", rolDAOImplementation.GetAll().objects);
//            model.addAttribute("paises", paisDAOImplementation.GetAll().objects);
//
//            int idPais = usuario.Direcciones.get(0).colonia.municipio.estado.pais.getIdPais();
//            int idEstado = usuario.Direcciones.get(0).colonia.municipio.estado.getIdEstado();
//            int idMunicipio = usuario.Direcciones.get(0).colonia.municipio.getIdMunicipio();
//            int idColonia = usuario.Direcciones.get(0).colonia.getIdColonia();
//
//         if (idEstado != 0) {
//               model.addAttribute("estados", estadoDAOImplementation.GetByID(idPais).objects);
//           
//            if (idMunicipio != 0) {
//                model.addAttribute("municipios", municipioDAOImplementation.GetById(idEstado).objects);
//            
//
//            if (idColonia != 0) {
//                model.addAttribute("colonias", coloniaDAOImplementation.GetByID(idMunicipio));
//            }
//            }
//         }
//            return "Formulario";
//
//        }

            String nombreArchivo = imagen.getOriginalFilename();

            //2. Cortar la palabra
            String[] cadena = nombreArchivo.split("\\.");
            if (cadena[1].equals(
                    "jpg") || cadena[1].equals("png")) {
                //convierto imagen a base 64, y la cargo en el modelo alumno 
                try {
                    byte[] fileContent = imagen.getBytes();

                    String encodedString = Base64.getEncoder().encodeToString(fileContent);

                    System.out.println(encodedString);

                    usuario.setImagen(encodedString);

                } catch (Exception ex) {
                    result.correct = false;
                    result.errorMessage = ex.getLocalizedMessage();
                    result.ex = ex;
                }

                // realizar la conversión de imagen a base 64; 
            } else if (imagen
                    != null) {
                System.out.println("Error");

                return "form";
                //retorno error de archivo no permititido y regreso a formulario 
            }

        } catch (Exception ex) {
            result.correct = false;
            result.errorMessage = ex.getLocalizedMessage();
            result.ex = ex;

        }

        System.out.println(
                "Agregar");
        model.addAttribute("usuario", usuario);
        result = usuarioDAOJPAImplementation.ADD(usuario);
        if (result.correct == false) {
            return "Formulario";
        }

        return "redirect:/usuario";
    }

    @GetMapping("/delete/{idUsuario}")
    public String Delete(@PathVariable("idUsuario") int idUsuario, RedirectAttributes redirecAttributes) {

        Result result = new Result();
        result = usuarioDAOJPAImplementation.Delete(idUsuario);

        if (result.correct == true) {
            redirecAttributes.addFlashAttribute("message", "Usuario eliminado");
            return ("redirect:/usuario");

        } else {
            return ("redirect:/usuario");
        }

    }

    @PostMapping("/update/{IdUsuario}")
    public String UpdateUsuario(@ModelAttribute("usuario") Usuario usuario, RedirectAttributes redirecAttribute, @PathVariable("IdUsuario") int identificador, Model model) {
        Result result = new Result();

        try {

            result = usuarioDAOJPAImplementation.Updateusuario(usuario);
            if (result.correct == false) {
                return "GetAll";
            }
        } catch (Exception e) {
            result.correct = false;
            result.errorMessage = e.getLocalizedMessage();
            result.ex = e;
        }

        return "GetAll";
    }

    @GetMapping("/GetById/{IdUsuario}")
    @ResponseBody
    public Result GetById(@PathVariable("IdUsuario") int identificador, Model model) {
        Result result = new Result();
        try {
            result = usuarioDAOJPAImplementation.GetById(identificador);

        } catch (Exception e) {
            result.correct = false;
            result.errorMessage = e.getLocalizedMessage();
            result.ex = e;
        }
        System.out.println("Funciona GetByIDPais");
        return result;
    }

    @GetMapping("/details/{IdUsuario}")
    public String Details(@PathVariable("IdUsuario") int identificador, Model model) {
        Result result = new Result();
        Usuario usuario = new Usuario();

        try {
            model.addAttribute("roles", rolDAOImplementation.GetAll().objects);
            model.addAttribute("paises", paisDAOImplementation.GetAll().objects);
            model.addAttribute("usuario", usuarioDAOImplementation.GetById(identificador).object);

            int idPais = usuario.Direcciones.get(0).colonia.municipio.estado.pais.getIdPais();
            int idEstado = usuario.Direcciones.get(0).colonia.municipio.estado.getIdEstado();
            int idMunicipio = usuario.Direcciones.get(0).colonia.municipio.getIdMunicipio();
            int idColonia = usuario.Direcciones.get(0).colonia.getIdColonia();

            if (idEstado != 0) {
                //guardo el valor
                model.addAttribute("estados", estadoDAOImplementation.GetByID(idPais).objects);
                if (idMunicipio != 0) {
                    //guardo el valor
                    model.addAttribute("municipios", municipioDAOImplementation.GetById(idEstado).objects);
                    if (idColonia != 0) {
                        //guardo el valor
                        model.addAttribute("colonias", coloniaDAOImplementation.GetByID(idMunicipio).objects);

                    }
                }

            }

        } catch (Exception e) {
            result.correct = false;
            result.errorMessage = e.getLocalizedMessage();
            result.ex = e;
        }
        System.out.println("Funciona GetByIDPais");

        return "Details";
    }

//    COLOCAR EL PROCEDURE O ACTUALIZAR EN LA BASE DE DATOS, NO OLVIDAR CAMBIAR LOS NOMBRES DE LAS TABLAS EN LA BD DE LA EMPRESA
//
//    
    @PostMapping("/updateimg/{IdUsuario}")
    public String UpdateImagen(@Valid @ModelAttribute("usuario") Usuario usuario, BindingResult bindingResult, @PathVariable("IdUsuario") int identificador, @RequestParam("imagen") MultipartFile imagen, Model model) {
        Result result = new Result();
        try {
            //  Verificar si se subió un archivo nuevo
            if (imagen != null && !imagen.isEmpty()) {
                String nombreArchivo = imagen.getOriginalFilename();
                String extension = nombreArchivo.substring(nombreArchivo.lastIndexOf(".") + 1).toLowerCase();

                if (extension.equals("jpg") || extension.equals("png") || extension.equals("jpeg")) {
                    // Leer bytes y convertir a Base64
                    byte[] fileContent = imagen.getBytes();
                    String encodedString = Base64.getEncoder().encodeToString(fileContent);
                    usuario.setImagen(encodedString);
                }
            } else {

                result = usuarioDAOJPAImplementation.GetById(identificador);
                if (result.correct) {
                    Usuario usuarioanterior = (Usuario) result.object;
                    usuario.setImagen(usuarioanterior.getImagen());
                }
            }

            // Ejecutar la actualización
            result = usuarioDAOImplementation.UpdateImagen(usuario);

            if (!result.correct) {
                return "redirect:/usuario/details/" + identificador;
            }

        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }

        return "redirect:/usuario/details/" + identificador;
    }

    @GetMapping("/cargamasiva")
    public String CargaMasiva() {

        return "CargaMasiva";
    }

    @PostMapping("/cargamasiva")
    public String CargaMasiva(@RequestParam("archivo") MultipartFile archivo, Model model, HttpSession session) {
        Result result = new Result();
        UUID uuid = UUID.randomUUID();
        System.out.println(uuid);
        try {
            if (archivo != null) {

                String rutaBase = System.getProperty("user.dir");
                String rutaCarpeta = "src/main/resources/archivosCM";
                String fecha = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmSS"));
                String nombreArchivo = fecha + archivo.getOriginalFilename();
                String rutaArchivo = rutaBase + "/" + rutaCarpeta + "/" + nombreArchivo;
                String extension = archivo.getOriginalFilename().split("\\.")[1];
                List<Usuario> usuarios = null;
                if (extension.equals("txt")) {
                    archivo.transferTo(new File(rutaArchivo));
                    usuarios = LecturaArchivoTxt(new File(rutaArchivo));
                } else if (extension.equals("xlsx")) {
                    archivo.transferTo(new File(rutaArchivo));
                    usuarios = LecturaArchivoExcel(new File(rutaArchivo));
                } else {
                    System.out.println("Extensión erronea, manda archivos del formato solicitado");
                }

                List<ErroresArchivo> errores = ValidarDatos(usuarios);

                if (errores.isEmpty()) {
                    
                    model.addAttribute("errores", false);
//                    se guarda info
                    session.setAttribute("ruta", uuid);
                } else {
                    model.addAttribute("errores",errores);
                }
              
            }
        } catch (Exception ex) {
            // notificación de error
            result.correct = false;
            result.errorMessage = ex.getLocalizedMessage();
            result.ex = ex;
            System.out.println(ex.getLocalizedMessage());
        }
        return "CargaMasiva";
    }

    public List<Usuario> LecturaArchivoTxt(File archivo) {
        Result result = new Result();
        List<Usuario> usuarios = new ArrayList<>();
        
        SimpleDateFormat formatoFecha = new SimpleDateFormat("dd/MM/yyyy");
        try (InputStream inputStream = new FileInputStream(archivo); BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream))) {
            usuarios = new ArrayList<>();
            String cadena = "";
            while ((cadena = bufferedReader.readLine()) != null) {
//                Nombre|ApellidoPaterno|Materno|Fecha
                String[] datosUsuario = cadena.split("\\|");
                Usuario usuario = new Usuario();
                usuario.Rol = new Rol();
                usuario.setNombre(datosUsuario[0]);
                usuario.setApellidoPaterno(datosUsuario[1]);
                usuario.setApellidoMaterno(datosUsuario[2]);
                try {
                    String fechanacimiento = datosUsuario[3].trim();
                    usuario.setFechaNacimiento(formatoFecha.parse(fechanacimiento));
                } catch (Exception ex) {
                    result.correct = false;
                    result.errorMessage = ex.getLocalizedMessage();
                    result.ex = ex;
                }
                usuario.setCURP(datosUsuario[4]);
                usuario.setEmail(datosUsuario[5]);
                usuario.setNumeroTelefonico(datosUsuario[6].toString());
                usuario.setSexo(datosUsuario[7]);
                usuario.setCelular(datosUsuario[8]);
                usuario.setUsername(datosUsuario[9]);
                try {
                    int IdRol = Integer.parseInt(datosUsuario[10].trim());
                    usuario.Rol.setidRol(IdRol);
                } catch (Exception ex) {
                    result.correct = false;
                    result.errorMessage = ex.getLocalizedMessage();
                    result.ex = ex;
                }
                usuario.setImagen(datosUsuario[11].trim());
                 
                    Direccion direccion = new Direccion();
                    direccion.colonia = new Colonia();
                    direccion.setCalle(datosUsuario[12].trim());
                    direccion.setNumeroExterior(datosUsuario[13].trim());
                    direccion.setNumeroInterior(datosUsuario[14].trim());
                    direccion.colonia.setIdColonia(Integer.parseInt(datosUsuario[15].trim()));

                    usuario.Direcciones = new ArrayList<>();
                    usuario.Direcciones.add(direccion);
                    usuarios.add(usuario);

                usuarios.add(usuario);
            }

        } catch (Exception ex) {
            result.correct = false;
            result.errorMessage = ex.getLocalizedMessage();
            return null;
        }

        return usuarios;
    }

    public List<Usuario> LecturaArchivoExcel(File archivo) {
        Result result = new Result();
        List<Usuario> usuarios = null;

        try (InputStream inputStream = new FileInputStream(archivo); XSSFWorkbook workbook = new XSSFWorkbook(inputStream)) {

            XSSFSheet sheet = workbook.getSheetAt(0);

            usuarios = new ArrayList<>();

            for (Row row : sheet) {
                Usuario usuario = new Usuario();

                usuario.setNombre(row.getCell(0).toString());
                usuario.setApellidoPaterno(row.getCell(1).toString());
                usuario.setApellidoMaterno(row.getCell(2).toString());
                usuario.setCURP(row.getCell(3).toString());
                usuario.setFechaNacimiento(row.getCell(4).getDateCellValue());

                usuarios.add(usuario);

            }

        } catch (Exception ex) {
            result.correct = false;
            result.errorMessage = ex.getLocalizedMessage();
            result.ex = ex;
        }

        return usuarios;
    }

    public List<ErroresArchivo> ValidarDatos(List<Usuario> usuarios) {
        Result result = new Result();
        List<ErroresArchivo> errores = new ArrayList<>();

        try {
            int fila = 1;

            for (Usuario usuario : usuarios) {
                BindingResult bindingResult = validationService.ValidateObject(usuario);

                if (bindingResult.hasErrors()) {

                    for (ObjectError objectError : bindingResult.getAllErrors()) {
                        
                        ErroresArchivo erroresArchivo = new ErroresArchivo();

                        erroresArchivo.dato = ((FieldError) objectError).getField();
                        erroresArchivo.descripcion = objectError.getDefaultMessage();
                        erroresArchivo.fila = fila;

                        errores.add(erroresArchivo);

                    }
                fila++;

                }
                else{
                    
                }

            }
        } catch (Exception ex) {
            result.correct = false;
            result.errorMessage = ex.getLocalizedMessage();
            result.ex = ex;

        }
        return errores;
    }

    @GetMapping("/cargamasiva/procesar/{UUID}")
    public String ProcesarCargaMasiva(@PathVariable ("UUID") UUID uuid,RedirectAttributes redirectAttributes, HttpSession session) {
        /*Procesar
        Aperturar archivo
        Inertar datos
         */
        // mensaje de confirmación de carga exitosa
        
        String rutaArchivo = session.getAttribute("ruta").toString();
        List <Usuario> usuario = LecturaArchivoExcel(new File(rutaArchivo));
        
        Result result = usuarioDAOImplementation.AddAll(usuario); //asignar en el dao el addall
        
        
        return "redirect:/usuario";
    }

    @GetMapping("getEstadosByPais/{idPais}")
    @ResponseBody
    public Result getEstadosByPais(@PathVariable("idPais") int idPais) {
        Result result = estadoDAOImplementation.GetByID(idPais);

        result.correct = true;
        return result;
    }

    @GetMapping("getMunicipioByEstado/{idEstado}")
    @ResponseBody
    public Result getMunicipioByEstado(@PathVariable("idEstado") int idEstado) {
        Result result = municipioDAOImplementation.GetById(idEstado);

        result.correct = true;
        return result;
    }

    @GetMapping("getColoniaByMunicipio/{idMunicipio}")
    @ResponseBody
    public Result getColoniaByMunicipio(@PathVariable("idMunicipio") int idMunicipio) {
        Result result = coloniaDAOImplementation.GetByID(idMunicipio);

        result.correct = true;
        return result;
    }
    
    @PostMapping("/updateStatus/{status}/{identificador}")
    public Result updateStatus (@PathVariable ("status") int status, @PathVariable ("identificador") int identificador){
        Result result = new Result();
        
        try{
        result = usuarioDAOImplementation.UpdateStatus(status, identificador);
        
        }catch(Exception ex){
            result.correct = false;
            result.errorMessage = ex.getLocalizedMessage();
            result.ex = ex;
            
        }
        
        return result;
    }

}
