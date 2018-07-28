/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import entities.Menu;
import entities.Operations;
import entities.Utilisateur;
import java.io.Serializable;
import java.sql.Time;
import java.util.Date;
import javax.ejb.EJB;
import javax.faces.context.FacesContext;
import javax.servlet.ServletRequest;
import javax.servlet.http.HttpSession;
import sessions.OperationsFacadeLocal;
import sessions.UtilisateurFacadeLocal;

/**
 *
 * @author KENGNI Hippolyte
 */
public class SessionController implements Serializable {

    @EJB
    private OperationsFacadeLocal operationsFacade;
    @EJB
    private UtilisateurFacadeLocal utilisateurFacade;
    private Utilisateur currentUser = new Utilisateur();
    private String langue = "fr";
    private String msg = "";
    private boolean fichier = false;
    private boolean traitem = false;
    private boolean analyse = false;
    private boolean utilisa = false;
    private boolean privile = false;
    private boolean mouchar = false;

    /**
     * Creates a new instance of SessionController
     */
    public SessionController() {
    }

    public void watchOut() {
        try {
            if (!FacesContext.getCurrentInstance().getExternalContext().getSessionMap().containsKey("currentUser")) {
                ((FacesContext.getCurrentInstance()).getExternalContext()).redirect("authenticate.xhtml?faces-redirect=true");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String authenticate() {
        try {
            currentUser = utilisateurFacade.findByLoginMdp(currentUser.getLogin(), ((Integer) currentUser.getMdp().hashCode()).toString());
            if (currentUser != null) {
                msg = "";
                FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("currentUser", currentUser);
                logFile("Connexion", "Système");
                //Configuration des accès
                fichier = currentUser.getMenuCollection().contains(new Menu("fichier"));
                traitem = currentUser.getMenuCollection().contains(new Menu("traitem"));
                analyse = currentUser.getMenuCollection().contains(new Menu("analyse"));
                utilisa = currentUser.getMenuCollection().contains(new Menu("utilisa"));
                privile = currentUser.getMenuCollection().contains(new Menu("privile"));
                mouchar = currentUser.getMenuCollection().contains(new Menu("mouchar"));
                return "index.xhtml?faces-redirect=true";
            } else {
                msg = "Login ou mot de passe invalide, veuillez réessayer...";
                currentUser = new Utilisateur();
                return "authenticate.xhtml?faces-redirect=true";
            }
        } catch (Exception e) {
            e.printStackTrace();
            msg = "Login ou mot de passe invalide, veuillez réessayer...";
            currentUser = new Utilisateur();
            return "authenticate.xhtml?faces-redirect=true";
        }
    }

    public String logOut() {
        try {
            msg = "";
            logFile("Déconnexion", "Système");
            FacesContext.getCurrentInstance().getExternalContext().getSessionMap().remove("currentUser");
            ((HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false)).invalidate();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            return "authenticate.xhtml?faces-redirect=true";
        }
    }

    public void logFile(String name, String target) {
        try {
            Operations op = new Operations();
            op.setDate(new Date(System.currentTimeMillis()));
            op.setAdresseip(((ServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest()).getRemoteAddr());
            op.setNom(name);
            op.setCible(target);
            op.setHeure(new Time(System.currentTimeMillis()));
            op.setIdutilisateur((Utilisateur) FacesContext.getCurrentInstance().getExternalContext().getSessionMap());
            operationsFacade.create(op);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String english() {
        langue = "en";
        return FacesContext.getCurrentInstance().getExternalContext().getRequestPathInfo() + "?face-redirect=true";
    }

    public String french() {
        langue = "fr";
        return FacesContext.getCurrentInstance().getExternalContext().getRequestPathInfo() + "?face-redirect=true";
    }

    public String getLangue() {
        return langue;
    }

    public void setLangue(String langue) {
        this.langue = langue;
    }

    public UtilisateurFacadeLocal getUtilisateurFacade() {
        return utilisateurFacade;
    }

    public void setUtilisateurFacade(UtilisateurFacadeLocal utilisateurFacade) {
        this.utilisateurFacade = utilisateurFacade;
    }

    public Utilisateur getCurrentUser() {
        return currentUser;
    }

    public void setCurrentUser(Utilisateur currentUser) {
        this.currentUser = currentUser;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public OperationsFacadeLocal getOperationsFacade() {
        return operationsFacade;
    }

    public void setOperationsFacade(OperationsFacadeLocal operationsFacade) {
        this.operationsFacade = operationsFacade;
    }

    public boolean isFichier() {
        return fichier;
    }

    public void setFichier(boolean fichier) {
        this.fichier = fichier;
    }

    public boolean isTraitem() {
        return traitem;
    }

    public void setTraitem(boolean traitem) {
        this.traitem = traitem;
    }

    public boolean isAnalyse() {
        return analyse;
    }

    public void setAnalyse(boolean analyse) {
        this.analyse = analyse;
    }

    public boolean isUtilisa() {
        return utilisa;
    }

    public void setUtilisa(boolean utilisa) {
        this.utilisa = utilisa;
    }

    public boolean isPrivile() {
        return privile;
    }

    public void setPrivile(boolean privile) {
        this.privile = privile;
    }

    public boolean isMouchar() {
        return mouchar;
    }

    public void setMouchar(boolean mouchar) {
        this.mouchar = mouchar;
    }
}
