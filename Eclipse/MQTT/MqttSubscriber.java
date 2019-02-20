package org.thingml.generated;

import java.util.function.Consumer;
import java.util.function.Function;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;

public class MqttSubscriber implements MqttCallback {
	MqttClient client; //this is a id for MQTT
	public Consumer<Short> f; //ThingML generated function, that passes MQTT data (as ThingML messeges) to a ThingML thing 
	public static MqttClient msgClient=null;//this is a id for MQTT

	public MqttSubscriber() {
	}

	//static function for sending commands to mqtt(Like: turn on switch 1)
	public static void sendComand(String serverAdr, String subj, String msg) {
		try {
		 	MqttClient client;
		 if(msgClient==null) {
			 client = new MqttClient(serverAdr, MqttClient.generateClientId()); 
		 }else{
			 client=msgClient;
		 }
	     	client.connect();
	        
	        MqttMessage message = new MqttMessage();
	        message.setPayload(msg.getBytes());
	        client.publish(subj, message);
	        
	    } catch (MqttException e) {
	       	e.printStackTrace();
	    }
	}
	
	//subscribes to a subject, all messeges on that subject will be recived in messageArrived()
	public void subTo(String serverAdr, String subj,Consumer<Short> f) {
		try {
			this.f=f;
			client = new MqttClient(serverAdr, MqttClient.generateClientId());
			client.connect();
			client.setCallback(this);
			client.subscribe(subj);
		} catch (MqttException e) {
    		e.printStackTrace();
    	}
	}

	//all messages on subscribed subject will arive here
	public void messageArrived(String topic, MqttMessage message)
		throws Exception {
		short msg=1;
		try {
			msg = (short)Integer.parseInt(message.toString());
			f.accept(msg);
		} catch (NumberFormatException e) {  
			if(message.toString().equals("ON")) {
				msg=1;
				f.accept(msg);
			}
		}
		System.out.println(message);
	}

	public void connectionLost(Throwable cause) {
	// TODO Auto-generated method stub
	}
	public void deliveryComplete(IMqttDeliveryToken token) {
    // TODO Auto-generated method stub
	}
}

/*//Easy access copy past data for me
<dependency>
       <groupId>org.eclipse.paho</groupId>
       <artifactId>org.eclipse.paho.client.mqttv3</artifactId>
       <version>1.2.0</version>
</dependency>
new MqttSubscriber().subTo("tcp://localhost:1883","#", this::sendSwitchOff_via_get_sensor);
*/