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
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.servlet.ServletOutputStream;
import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletResponse;
import static net.sf.jasperreports.engine.JasperExportManager.exportReportToPdfStream;
import static net.sf.jasperreports.engine.JasperFillManager.fillReport;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.primefaces.context.RequestContext;
import org.primefaces.component.commandbutton.CommandButton;
import sessions.OperationsFacadeLocal;
import sessions.UtilisateurFacadeLocal;

/**
 *
 * @author KENGNI Hippolyte
 */
public class UtilisateurController implements Serializable {
    
    @EJB
    private OperationsFacadeLocal operationsFacade;

    @EJB
    private UtilisateurFacadeLocal utilisateurFacade;
    private List<Utilisateur> listUtilisateur = new ArrayList<>();
    private Utilisateur utilisateur = new Utilisateur();
    private String operation;
    private String msg;

    /**
     * Creates a new instance of UtilisateurController
     */
    public UtilisateurController() {
    }

    @PostConstruct
    public void init() {
        listUtilisateur.clear();
        listUtilisateur.addAll(utilisateurFacade.findAll());
    }
    
    public void logFile(String name, String target){
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

    public void action(ActionEvent e) {
        CommandButton btn = (CommandButton) e.getSource();
        operation = btn.getWidgetVar();
        msg = "";
    }

    public void prepareCreate(ActionEvent e) {
        utilisateur = new Utilisateur();
        msg = "";
        action(e);
    }

    public void saveAccount() {
        try {
            if (utilisateurFacade.findByLogin(utilisateur.getLogin()).isEmpty()) {
                utilisateur.setIdutilisateur(utilisateurFacade.nextId());
                utilisateur.setMdp(((Integer) utilisateur.getMdp().hashCode()).toString());
                utilisateurFacade.create(utilisateur);
                logFile("Enregistrer un utilisateur", utilisateur.getNom() + " " + utilisateur.getPrenom());
                msg = "Opération effectuée avec succès";
                RequestContext.getCurrentInstance().execute("PF('wv_utilisateur').hide()");
            } else {
                msg = "Ce login est déjà utilisé, veuillez choisir un autre!";
            }
        } catch (Exception e) {
            e.printStackTrace();
            msg = "Echec de l'opération";
        } finally {
            init();
        }
    }

    public void modifyAccount() {
        try {
            if (utilisateurFacade.findByLogin(utilisateur.getLogin()).isEmpty()) {
                utilisateur.setMdp(((Integer) utilisateur.getMdp().hashCode()).toString());
                utilisateurFacade.edit(utilisateur);
                logFile("Modifier un utilisateur", utilisateur.getNom() + " " + utilisateur.getPrenom());
                msg = "Opération effectuée avec succès!";
                RequestContext.getCurrentInstance().execute("PF('wv_utilisateur').hide()");
            } else {
                msg = "Ce login est déjà utilisé, veuillez choisir un autre!";
            }
        } catch (Exception e) {
            e.printStackTrace();
            msg = "Echec de l'opération";
        } finally {
            init();
        }
    }

    public void deleteAccount() {
        try {
            utilisateurFacade.remove(utilisateur);
            logFile("Supprimer un utilisateur", utilisateur.getNom() + " " + utilisateur.getPrenom());
            msg = "Opération effectuée avec succès!";
            RequestContext.getCurrentInstance().execute("PF('wv_utilisateur').hide()");
        } catch (Exception e) {
            e.printStackTrace();
            msg = "Echec de l'opération!";
        } finally {
            init();
        }
    }

    public void persist() {
        switch (operation) {
            case "add":
                saveAccount();
                break;
            case "modify":
                modifyAccount();
                break;
            case "delete":
                deleteAccount();
                break;
            case "print":
                imprimer();
                break;
        }
    }

    public String imprimer() {
        try {
            logFile("Imprimer la liste utilisateurs", "");
            JRBeanCollectionDataSource beanCollectionDataSource = new JRBeanCollectionDataSource(listUtilisateur);
            String reportPath = FacesContext.getCurrentInstance().getExternalContext().getRealPath("resources/etats/listUtilisateur.jasper");
            Map parameters = new HashMap();
            //parameters.put("USER", connectedUser);
            parameters.put("REPORT_LOCALE", FacesContext.getCurrentInstance().getViewRoot().getLocale());
            JasperPrint jasperPrint = fillReport(reportPath, parameters, beanCollectionDataSource);
            HttpServletResponse httpServletResponse = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
            httpServletResponse.addHeader("Content-disposition", "attachement; filename=listeUtilisateur.pdf");
            ServletOutputStream servletOutputStream = httpServletResponse.getOutputStream();
            exportReportToPdfStream(jasperPrint, servletOutputStream);
            FacesContext.getCurrentInstance().responseComplete();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return FacesContext.getCurrentInstance().getExternalContext().getRequestPathInfo() + "?faces-redirect=true";
    }

    public UtilisateurFacadeLocal getUtilisateurFacade() {
        return utilisateurFacade;
    }

    public void setUtilisateurFacade(UtilisateurFacadeLocal utilisateurFacade) {
        this.utilisateurFacade = utilisateurFacade;
    }

    public List<Utilisateur> getListUtilisateur() {
        return listUtilisateur;
    }

    public void setListUtilisateur(List<Utilisateur> listUtilisateur) {
        this.listUtilisateur = listUtilisateur;
    }

    public Utilisateur getUtilisateur() {
        return utilisateur;
    }

    public void setUtilisateur(Utilisateur utilisateur) {
        this.utilisateur = utilisateur;
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
