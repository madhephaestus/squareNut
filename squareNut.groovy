import eu.mihosoft.vrl.v3d.CSG
import eu.mihosoft.vrl.v3d.Cube
import eu.mihosoft.vrl.v3d.Cylinder
import eu.mihosoft.vrl.v3d.parametrics.*;
import com.neuronrobotics.bowlerstudio.vitamins.Vitamins;

import com.neuronrobotics.bowlerstudio.vitamins.Vitamins;
import javafx.scene.paint.Color;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;


	
import eu.mihosoft.vrl.v3d.parametrics.*;
CSG getNut(){
	String type= "squareNut"
	LengthParameter facets		= new LengthParameter("Bolt Hole Facet Count",10,[40,10])
	LengthParameter boltLength		= new LengthParameter("Bolt Length",10,[180,10])
	LengthParameter offset		= new LengthParameter("printerOffset",0.0,[2,0])
	if(args==null)
		args=["M3"]
	String sizeVar = args.get(0)
	StringParameter size = new StringParameter(	type+" Default",
										sizeVar,
										Vitamins.listVitaminSizes(type))
	//println "Database loaded "+database
	HashMap<String,Object> servoConfig = Vitamins.getConfiguration( type,size.getStrValue())
	double w=Double.parseDouble(servoConfig.get("width").toString())+offset.getMM()
	double h=Double.parseDouble(servoConfig.get("height").toString())

	
	CSG head =new Cube(w,w,h).toCSG() // a one line Cylinder
				.toZMin()

	return head
		.setParameter(size)
		.setParameter(boltLength)
		.setParameter(facets)
		.setParameter(offset)
		.setRegenerate({getNut()})
}
//println "Hello World from Cap Screw script!"
return getNut()