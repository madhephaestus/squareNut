import eu.mihosoft.vrl.v3d.CSG
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
	String type= "capScrew"
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
	headDiameter=Double.parseDouble(servoConfig.get("headDiameter").toString())+offset.getMM()
	headHeight=Double.parseDouble(servoConfig.get("headHeight").toString())
	keyDepth=Double.parseDouble(servoConfig.get("keyDepth").toString())
	keySize=Double.parseDouble(servoConfig.get("keySize").toString())
	outerDiameter=Double.parseDouble(servoConfig.get("outerDiameter").toString())+offset.getMM()
	if(servoConfig.get("boltLength")!=null)
		boltLength.setMM(servoConfig.get("boltLength"))

	
	CSG head =new Cylinder(headDiameter/2,headDiameter/2,headHeight,(int)facets.getMM()).toCSG() // a one line Cylinder
				.toZMin()
	CSG shaft =new Cylinder(outerDiameter/2,outerDiameter/2,boltLength.getMM(),(int)facets.getMM()).toCSG() // a one line Cylinder
				.toZMax()
	
	return head.union(shaft)
		.setParameter(size)
		.setParameter(boltLength)
		.setParameter(facets)
		.setParameter(offset)
		.setRegenerate({getNut()})
}
//println "Hello World from Cap Screw script!"
return getNut()