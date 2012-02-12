package org.xoliocraft.protect;

import java.io.*;
//import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

//import org.bukkit.ChatColor;

public class FileHandler {
		static File Cubosfile = new File("plugins/XolioProtect/cubos.txt");
		static FileInputStream fis;
		static FileOutputStream fos;
		static void load()
		{
			// --------------------------------------------------------------------------------------------------
			// ------------------------ START BY THE CUBOSSSsssssssss -------------------------------- BOUM -----
			// --------------------------------------------------------------------------------------------------
			if(!Cubosfile.exists())
			{
				Protect.log.info("[XolioProtect]Ooops no config ! Trying to create one ...");
				try
				{
					File fb = new File("plugins/XolioProtect/"); 
					fb.mkdirs();
					Protect.log.info("[XolioProtect]Created plugins/XolioProtect..");
				}
				catch (Exception e)
				{
					Protect.log.info("[XolioProtect]Unable create folder..");
				}
				try
				{
					Cubosfile = new File("plugins/XolioProtect/cubos.txt");
					//Cubosfile.setWritable(true);
					Cubosfile.createNewFile();
				}
				catch (IOException e)
				{
					Protect.log.info("[XolioProtect]Unable to create it..");
				}
				try {
					fos = new FileOutputStream(new File("plugins/XolioProtect/cubos.txt"));
				} catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				writeln("#Raw & empty file");
				try {
					fos.close();
					Protect.log.info("[XolioProtect]Done. No errors reported.");
				} catch (IOException e) {
					// TODO Auto-generated catch block
					Protect.log.info("[XolioProtect]Error , could'nt create an empty cubo file :/");
				}
			}
			Protect.log.info("[XolioProtect]Loading cuboids...");
				//String contenu = "";
					try {
						fis = new FileInputStream(new File("plugins/XolioProtect/cubos.txt"));
					} catch (FileNotFoundException e) {
						e.printStackTrace();
					}
					
					byte[] buf = new byte[1];
					@SuppressWarnings("unused")
					int n = 0;
					char c =' ';
					String line = "";
					String name = "";
					//Map<Integer,boolean[]> flags;0;
					Cuboid toadd = null;
					//Map<String,Integer> flags = new HashMap<String,Integer>();
						try {
							while((n=fis.read(buf)) > 0)
							{
								
								for(byte bit : buf)
								{
									
									//System.out.print((char)bit);
									c= (char)bit;
								}
								if(c == '\n')
								{
									//System.out.println("HOLY SH**** A LINE ");
									if(!(line.charAt(0) == '#'))
									{
										if(line.charAt(0) == '@')
										{
											//System.out.println("Itz a cubo define !");
											name = line.substring(10,line.length());
											toadd = new Cuboid(name);
											//System.out.println(name+" is hard work");
										}
										
										if(line.charAt(0) == '*')
										{
											Protect.log.info("[XolioProtect]Cuboid "+name+" loaded.");
										//owner = line.substring(1,line.length());
										CuboidsList.addCuboid(name, toadd);
										}
										if(line.charAt(0) == '!')
										{
											@SuppressWarnings("unused")
											String prop;
											//System.out.println("Itz a proprety !");
											prop = line.substring(1,line.length());
											String propvalue;

											String propname;
											for(int a=1;a<line.length();a++){
												if(line.charAt(a) == '=')
												{
													propname = line.substring(1, a);
													propvalue= line.substring(a+1,line.length());
													//System.out.print("Debug : Prop : "+propname+"="+propvalue);
													toadd.setProp(propname, propvalue);
												}
											}
												
										}
										if(line.charAt(0) == '&')
										{
											String propname,propvalue;
											for(int a=1;a<line.length();a++){
												if(line.charAt(a) == '=')
												{
													propname = line.substring(1, a);
													propvalue= line.substring(a+1,line.length());
													//System.out.print("Debug : Flag : "+propname+"="+propvalue);
													toadd.setFlag(propname, Integer.parseInt(propvalue));
												}
											}
											
										}
									}
								// analyse the line :D
								line = "";
								}
								else
								{
									line = line + c ;
								}
									
							}
							Protect.log.info("[XolioProtect]Done with loading cuboids.");
						} catch (IOException e) {
							
							e.printStackTrace();
						}

					try {
							fis.close();
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
			
		}
		@SuppressWarnings("unchecked")
		static void save()
		{
			Protect.log.info("[XolioProtect]Saving cuboids...");
			try {
				fos = new FileOutputStream(new File("plugins/XolioProtect/cubos.txt"));
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			// #######################################################################################
			writeln("# XolioProtect cuboid file auto generated, don't modify. Use ingame cmds.");
			Set<String> cubos = CuboidsList.getRaw().keySet();
			String cles[] = cubos.toArray(new String[0]);
			@SuppressWarnings("unused")
			Map<String,Integer> flags;
			int n = cubos.size();
			int x = 0;
			while(x < n)
			{
				Cuboid testable;
				testable = CuboidsList.getRaw().get(cles[x]);
				writeln("@cuboname:"+cles[x]);
				flags = testable.getAllFlags();
				//writeln("!world="+testable.world);
				//writeln("!owner="+testable.getOwner());
				//int pos[] = testable.getPos();
				/*writeln("!sx="+pos[0]);
				writeln("!sy="+pos[1]);
				writeln("!sz="+pos[2]);
				writeln("!ex="+pos[3]);
				writeln("!ey="+pos[4]);
				writeln("!ez="+pos[5]);*/
				for(Iterator i=testable.getAllFlags().keySet().iterator();i.hasNext();){
					String flagname = (String) i.next();
					writeln("&"+flagname+"="+testable.getFlag(flagname));
				}
				for(Iterator i=testable.getAllProps().keySet().iterator();i.hasNext();){
					String flagname = (String) i.next();
					writeln("!"+flagname+"="+testable.getProp(flagname));
				}
				x++;
				writeln("*endcubo");
			}
			// #######################################################################################
			try {
				fos.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}	
		}
		static void writeln(String txt)
		{
			int x,z = 0;
			x = txt.length();
			while(z < x)
			{
				try {
					fos.write(txt.charAt(z));
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				z++;
			}
			try {
				fos.write('\n');
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	
}
