/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package topology.storage;

/**
 *
 * @author Mao
 */
public interface IStorageComponent {
    public void setCode(String code);
    public void setCapacity(int capacity);
    public void addComponent(Object object);
}
