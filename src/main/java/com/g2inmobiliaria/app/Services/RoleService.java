package com.g2inmobiliaria.app.Services;

import com.g2inmobiliaria.app.Entities.Role;
import jakarta.persistence.EntityManager;
import jakarta.persistence.ParameterMode;
import jakarta.persistence.StoredProcedureQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleService {
    @Autowired
    private EntityManager entityManager;

    // Método para listar Roles
    public List<Role> listarRoles() {
        StoredProcedureQuery query = entityManager.createStoredProcedureQuery("spGetRole", Role.class);
        return query.getResultList();
    }

    // Método para registrar un Rol y obtener el ID del rol creado
    public String registrarRole(Role role) {
        StoredProcedureQuery query = entityManager.createStoredProcedureQuery("spCreateRole");
        query.registerStoredProcedureParameter(1, String.class, ParameterMode.IN);
        query.registerStoredProcedureParameter(2, Integer.class, ParameterMode.OUT); // Parámetro de salida para el ID del rol creado

        query.setParameter(1, role.getRoleName());

        query.execute();

        // Obtener el valor del parámetro de salida
        int idRoleCreado = (int) query.getOutputParameterValue(2);

        if(idRoleCreado > 0){
            return "Role creado exitosamente";
        } else if (idRoleCreado == -1) {
            return "El nombre del rol ya existe";
        } else{
            return "Hubo un error al registrar el role";
        }
    }

    public String actualizarRole(Role role) {
        StoredProcedureQuery query = entityManager.createStoredProcedureQuery("spUpdateRole");
        query.registerStoredProcedureParameter("IdRole", Integer.class, ParameterMode.IN);
        query.registerStoredProcedureParameter("RoleName", String.class, ParameterMode.IN);
        query.registerStoredProcedureParameter("ErrorCode", Integer.class, ParameterMode.OUT);

        query.setParameter("IdRole", role.getId()); // Assuming you have a getId() method in your Role entity
        query.setParameter("RoleName", role.getRoleName());

        query.execute();

        int errorCode = (int) query.getOutputParameterValue("ErrorCode");
        System.out.println(errorCode);
        if (errorCode == 0){
            return "{\"success\": true, \"message\": \"¡Rol actualizado exitosamente!\"}";
        } else {
            return "{\"success\": false, \"message\": \"¡Hubo un error actualizando!\"}";
        }
    }

    public String borradoLogicoRole(int id) {
        StoredProcedureQuery query = entityManager.createStoredProcedureQuery("spLogicalDeleteRole");
        query.registerStoredProcedureParameter("IdRole", Integer.class, ParameterMode.IN);
        query.registerStoredProcedureParameter("IdRoleEliminado", Integer.class, ParameterMode.OUT);

        query.setParameter("IdRole", id);

        query.execute();

        int idRoleEliminado = (int) query.getOutputParameterValue("IdRoleEliminado");
        System.out.println(idRoleEliminado);
        if (idRoleEliminado == id){
            return "{\"success\": true, \"message\": \"Role deshabilitado exitosamente.\"}";
        } else {
            return "{\"success\": false, \"message\": \"Hubo un error desahbilitando el role.\"}";
        }
    }

    public Role obtenerRolePorId(int id){
        for(Role role : listarRoles()){
            if (id == role.getId()){
                return role;
            }
        }
        return null;
    }

}
