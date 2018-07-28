/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sessions;

import entities.Operations;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author KENGNI Hippolyte
 */
@Local
public interface OperationsFacadeLocal {

    void create(Operations operations);

    void edit(Operations operations);

    void remove(Operations operations);

    Operations find(Object id);

    List<Operations> findAll();

    List<Operations> findRange(int[] range);

    int count();
    
}
