/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import entities.Operations;
import entities.Utilisateur;
import java.io.Serializable;
import java.sql.Time;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.context.FacesContext;
import javax.servlet.ServletRequest;
import sessions.OperationsFacadeLocal;

/**
 *
 * @author KENGNI Hippolyte
 */
public class OperationsController implements Serializable {

    @EJB
    private OperationsFacadeLocal operationsFacade;
    private List<Operations> listOperations = new ArrayList<>();
    private Operations operations = new Operations();
    private String operation;
    private String msg;

    /**
     * Creates a new instance of OperationsController
     */
    public OperationsController() {
    }

    @PostConstruct
    public void init() {
        listOperations.clear();
        listOperations.addAll(operationsFacade.findAll());
    }

    public void logFile(String name, String target) {
        try {
            Operations op = new Operations();
            op.setDate(new Date(System.currentTimeMillis()));
            op.setAdresseip(((ServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest()).getRemoteAddr());
            op.setNom(name);
            op.setCible(target);
            op.setHeure(new Time(System.currentTimeMillis()));
            op.setIdutilisateur((Utilisateur) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("currentUser"));
            operationsFacade.create(op);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void deleteAll() {
        try {
            for (Operations o : listOperations) {
                operationsFacade.remove(o);
            }
            logFile("Supprimer toutes les opérations", "");
            msg = "Opération effectuée avec succès!";
        } catch (Exception e) {
            e.printStackTrace();
            msg = "Echec de l'opération";
        } finally {
            init();
        }
    }

    public OperationsFacadeLocal getOperationsFacade() {
        return operationsFacade;
    }

    public void setOperationsFacade(OperationsFacadeLocal operationsFacade) {
        this.operationsFacade = operationsFacade;
    }

    public List<Operations> getListOperations() {
        return listOperations;
    }

    public void setListOperations(List<Operations> listOperations) {
        this.listOperations = listOperations;
    }

    public Operations getOperations() {
        return operations;
    }

    public void setOperations(Operations operations) {
        this.operations = operations;
    }

    public String getOperation() {
        return operation;
    }

    public void setOperation(String operation) {
        this.operation = operation;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
