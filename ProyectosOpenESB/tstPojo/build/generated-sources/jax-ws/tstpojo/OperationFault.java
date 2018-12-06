
package tstpojo;

import javax.xml.ws.WebFault;


/**
 * This class was generated by the JAX-WS RI.
 * JAX-WS RI 2.2.6-1b01 
 * Generated source version: 2.2
 * 
 */
@WebFault(name = "myFault", targetNamespace = "http://xml.netbeans.org/schema/MyCalculatorSchema")
public class OperationFault
    extends Exception
{

    /**
     * Java type that goes as soapenv:Fault detail element.
     * 
     */
    private OutputFaultComplexType faultInfo;

    /**
     * 
     * @param faultInfo
     * @param message
     */
    public OperationFault(String message, OutputFaultComplexType faultInfo) {
        super(message);
        this.faultInfo = faultInfo;
    }

    /**
     * 
     * @param faultInfo
     * @param cause
     * @param message
     */
    public OperationFault(String message, OutputFaultComplexType faultInfo, Throwable cause) {
        super(message, cause);
        this.faultInfo = faultInfo;
    }

    /**
     * 
     * @return
     *     returns fault bean: tstpojo.OutputFaultComplexType
     */
    public OutputFaultComplexType getFaultInfo() {
        return faultInfo;
    }

}
