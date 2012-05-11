/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package topology.configuration;

/**
 *
 * @author Mao
 */
public abstract class AbstractComponent {
    public abstract void init(String code, int capacity);
    public abstract void suspend();
    public abstract void resume();
    public abstract String info();
    public abstract String getCode();
}
  