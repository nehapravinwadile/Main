import CalculatorApp.*;
import CalculatorApp.CalculatorHelper;
import org.omg.CORBA.*;
import org.omg.PortableServer.*;
import org.omg.PortableServer.POA;
import CalculatorApp.*;
import CalculatorApp.CalculatorHelper;
import org.omg.CORBA.*;
import org.omg.CosNaming.*;
import org.omg.CosNaming.NamingContextExt;
import org.omg.CosNaming.NamingContextExtHelper;
class CalculatorImpl extends CalculatorPOA {
 private ORB orb;
 public void setORB(ORB orb) {
this.orb = orb;
 }
 public float add(float a, float b) {
 return a + b;
 }
 public float subtract(float a, float b) {
 return a - b;
 }
 public float multiply(float a, float b) {
 return a * b;
 }
 public float divide(float a, float b) {
 if (b == 0) {
 throw new RuntimeException("Division by zero!");
 }
 return a / b;
 }
}
public class CalculatorServer {
 public static void main(String[] args) {
 try {
 // Initialize the ORB
 ORB orb = ORB.init(args, null);
 // Get reference to root POA and activate POA manager
 POA rootPoa = POAHelper.narrow(orb.resolve_initial_references("RootPOA"));
rootPoa.the_POAManager().activate();
 // Create servant and register it with the ORB
CalculatorImplcalculatorImpl = new CalculatorImpl();
calculatorImpl.setORB(orb);
 // Get object reference from servant
org.omg.CORBA.Object ref = rootPoa.servant_to_reference(calculatorImpl);
 Calculator href = CalculatorHelper.narrow(ref);
 // Bind the object reference in the naming service
org.omg.CORBA.ObjectobjRef = orb.resolve_initial_references("NameService");
NamingContextExtncRef = NamingContextExtHelper.narrow(objRef);
 String name = "Calculator";
NameComponent[] path = ncRef.to_name(name);
ncRef.rebind(path, href);
System.out.println("CalculatorServer ready and waiting ...");
 // Wait for incoming requests
orb.run();
 } catch (Exception e) {
e.printStackTrace();
 }
 }
}