//package com.gpai.util;
//
//import java.io.StringReader;
//import java.io.StringWriter;
//import java.util.List;
//
//import javax.annotation.Resource;
//import javax.xml.bind.JAXBContext;
//import javax.xml.bind.JAXBException;
//import javax.xml.bind.Marshaller;
//import javax.xml.bind.Unmarshaller;
//import javax.xml.parsers.ParserConfigurationException;
//import javax.xml.parsers.SAXParserFactory;
//import javax.xml.transform.Source;
//import javax.xml.transform.sax.SAXSource;
//
//import org.xml.sax.InputSource;
//import org.xml.sax.SAXException;
//import org.xml.sax.XMLReader;
//
//import com.gpai.auction.entity.Product;
//import com.gpai.auction.entity.gy.公告及拍卖标的信息_1020;
//import com.gpai.auction.entity.gy.拍卖标的信息1;
//import com.gpai.auction.mapper.product.ProductDao;
//import com.gpai.auction.service.ProductService;
//import com.gpai.auction.service.UserService;
//import com.gpai.auction.service.impl.ProductServiceImpl;
//import com.gpai.auction.service.impl.UserServiceImpl;
//import com.gpai.util.data.DateAddSub;
//  
///** 
// * Jaxb2工具类 
// * @author      zhuc 
// * @create      2013-3-29 下午2:40:14 
// */  
//public class JaxbUtil {  
//
//	@Resource(name = "productServiceImpl")
//	private static ProductService productService;
//    /** 
//     * JavaBean转换成xml 
//     * 默认编码UTF-8 
//     * @param obj 
//     * @param writer 
//     * @return  
//     */  
//    public static String convertToXml(Object obj) {  
//        return convertToXml(obj, "UTF-8");  
//    }  
//  
//    /** 
//     * JavaBean转换成xml 
//     * @param obj 
//     * @param encoding  
//     * @return  
//     */  
//    public static String convertToXml(Object obj, String encoding) {  
//        String result = null;  
//        try {  
//            JAXBContext context = JAXBContext.newInstance(obj.getClass());  
//            Marshaller marshaller = context.createMarshaller();  
//            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);  
//            marshaller.setProperty(Marshaller.JAXB_ENCODING, encoding);  
//  
//            StringWriter writer = new StringWriter();  
//            marshaller.marshal(obj, writer);  
//            result = writer.toString();  
//        } catch (Exception e) {  
//            e.printStackTrace();  
//        }  
//  
//        return result;  
//    }  
//  
//    /** 
//     * xml转换成JavaBean 
//     * @param xml 
//     * @param c 
//     * @return 
//     */  
//    @SuppressWarnings("unchecked")  
//    public static <T> T converyToJavaBean(String xml, Class<T> c) {  
//        T t = null;  
//        try {  
//            JAXBContext context = JAXBContext.newInstance(c);  
//            Unmarshaller unmarshaller = context.createUnmarshaller();  
//            t = (T) unmarshaller.unmarshal(new StringReader(xml));  
//        } catch (Exception e) {  
//            e.printStackTrace();  
//        }  
//  
//        return t;  
//        
//       
//    }  
//    public static <T> T xml2bean(Class<?> cla , String content){
//    	T t = null;
//        try {
//        	JAXBContext jaxbContext = JAXBContext.newInstance(cla);
//            Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
//            StringReader reader = new StringReader(content);
//            
//            SAXParserFactory sax = SAXParserFactory.newInstance();
//            sax.setNamespaceAware(false);
//            XMLReader xmlReader = sax.newSAXParser().getXMLReader();
//            
//            Source source = new SAXSource(xmlReader, new InputSource(reader));
//			t = (T) unmarshaller.unmarshal(source);
//		} catch (JAXBException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}catch (SAXException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (ParserConfigurationException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//
//        return t ;
//    } 
//  
//
//}  
