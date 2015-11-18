package com.fengjing.framework.webservice.springws.client.saaj;

import java.io.StringReader;
import java.net.URL;

import javax.xml.namespace.QName;
import javax.xml.soap.MessageFactory;
import javax.xml.soap.SOAPBody;
import javax.xml.soap.SOAPBodyElement;
import javax.xml.soap.SOAPEnvelope;
import javax.xml.soap.SOAPMessage;
import javax.xml.soap.SOAPPart;
import javax.xml.transform.Source;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMResult;
import javax.xml.transform.stream.StreamSource;
import javax.xml.ws.Dispatch;
import javax.xml.ws.Service;

import org.junit.Test;
import org.w3c.dom.Document;

/**
 * @see http
 *      ://localhost:8080/maven-framework/sws/services/studentDetails.wsdl
 * @author scott
 *
 */
public class StudentClientDetails {

    public static final String NAMESPACE_URI = "http://www.example.org/student";

    public static final String PREFIX = "tns";

    private URL url;

    @Test
    public void sendRequesetBySOAPMessage() throws Exception {

        /* SOAP��Ϣ
         * <?xml version="1.0" encoding="UTF-8"?>
         * <SOAP-ENV:Envelope xmlns:SOAP-ENV="http://schemas.xmlsoap.org/soap/envelope/">
         * <SOAP-ENV:Header />
         * <SOAP-ENV:Body>
         * 			<tns:studentRequest xmlns:tns="http://www.example.org/student">
         * 				<Name>ʹ��SpringWS����������Լ���ȵ�WebService</Name>
         * 				<Subject1>1</Subject1>
         * 				<Subject2>3</Subject2>
         * 				<Subject3>5</Subject3>
         * 			</tns:studentRequest>
         * </SOAP-ENV:Body>
         * </SOAP-ENV:Envelope>
         */

        /**************** ����SOAP��Ϣ *********************/
        //1������MessageFactory
        MessageFactory messageFactory = MessageFactory.newInstance();
        //2������MessageFactory����SOAPMessage
        SOAPMessage message = messageFactory.createMessage();
        //3������SOAPMessage��ȡSOAPPart
        SOAPPart soapPart = message.getSOAPPart();
        //4������SOAPPart��ȡSOAPEnvelope
        SOAPEnvelope soapEnvelope = soapPart.getEnvelope();
        //5������SOAPEnvelope��ȡSOAPBody
        SOAPBody soapBody = soapEnvelope.getBody();

        //6������SOAP��ϢSOAPBodyElement
        QName qname = new QName(NAMESPACE_URI, "studentRequest", PREFIX);
        //7������QName����SOAPBodyElement
        SOAPBodyElement soapBodyElement = soapBody.addBodyElement(qname);
        soapBodyElement.addChildElement("Name").setValue("ʹ��SpringWS����������Լ���ȵ�WebService");
        soapBodyElement.addChildElement("Subject1").setValue("1");
        soapBodyElement.addChildElement("Subject2").setValue("3");
        soapBodyElement.addChildElement("Subject3").setValue("5");
        message.saveChanges();

        System.out.println("������SOAP��Ϣ��Ϊ:");
        message.writeTo(System.out);

        /**************** ����SOAP��Ϣ *********************/
        //1������wsdl URL��ַ
        url = new URL("http://localhost:8080/maven-framework/sws/services/studentDetails.wsdl");
        /*
         *2������Service
         *��һ������Ϊwsdl URL��ַ,
         *�ڶ�������WebService service��name StudentDetailsService �μ�wsdl�е�<wsdl:service name="StudentDetailsService"> 
         */
        Service service = Service.create(url, new QName(NAMESPACE_URI, "StudentDetailsService"));
        /*
         * 3������Dispatch
         * ��һ������ΪportName �μ�wsdl�е�<wsdl:port binding="tns:StudentDetailsSoap11" name="StudentDetailsSoap11">
         * �ڶ�������ΪSOAP��Ϣ���������͵� ��������ʹ��SOAPMessage
         * ������������ָ��Ϣ�������������͵� MESSAGE provides access to entire protocol message, PAYLOAD to protocol message
         */
        Dispatch<SOAPMessage> dispatch = service.createDispatch(new QName(NAMESPACE_URI,
                "StudentDetailsSoap11"), SOAPMessage.class, Service.Mode.MESSAGE);
        //4������dispatch��invoke���� �������湹����SOAPMessage����webservice����,���ص�Ҳ��SOAPMessage
        SOAPMessage soapMessage = dispatch.invoke(message);
        System.out.println("\n��Ӧ��SOAP��Ϣ��Ϊ:");
        soapMessage.writeTo(System.out);

        //������Ϣ
        /*
         * <SOAP-ENV:Envelope xmlns:SOAP-ENV="http://schemas.xmlsoap.org/soap/envelope/">
         * 		<SOAP-ENV:Header/>
         * 			<SOAP-ENV:Body>
         * 				<ns2:studentResponse xmlns:ns2="http://www.example.org/student" xmlns:SOAP-ENV="http://schemas.xmlsoap.org/soap/envelope/" xmlns:xml="http://www.w3.org/XML/1998/namespace">
         * 				<ns2:Name>ʹ��SpringWS����������Լ���ȵ�WebService</ns2:Name>
         * 				<ns2:Department>MCA</ns2:Department>
         * 				<ns2:Subject1>1</ns2:Subject1>
         * 				<ns2:Subject2>3</ns2:Subject2>
         * 				<ns2:Subject3>5</ns2:Subject3>
         * 				<ns2:Total>9</ns2:Total>
         * 				</ns2:studentResponse>
         * 		</SOAP-ENV:Body>
         * </SOAP-ENV:Envelope>
         */
        Document document = soapMessage.getSOAPPart().getEnvelope().getBody()
                .extractContentAsDocument();

        String name = document.getElementsByTagNameNS(NAMESPACE_URI, "Name").item(0)
                .getTextContent();
        String department = document.getElementsByTagNameNS(NAMESPACE_URI, "Department").item(0)
                .getTextContent();
        String subject1 = document.getElementsByTagNameNS(NAMESPACE_URI, "Subject1").item(0)
                .getTextContent();
        String subject2 = document.getElementsByTagNameNS(NAMESPACE_URI, "Subject2").item(0)
                .getTextContent();
        String subject3 = document.getElementsByTagNameNS(NAMESPACE_URI, "Subject3").item(0)
                .getTextContent();
        String total = document.getElementsByTagNameNS(NAMESPACE_URI, "Total").item(0)
                .getTextContent();

        System.out.println("\n�����������:\nName:" + name + "\nDepartment:" + department
                + "\nSubject1:" + subject1 + "\nSubject2:" + subject2 + "\nSubject3:" + subject3
                + "\nTotal:" + total);

    }

    /**
     * ʹ��PAYLOAD��ʽ��Web���񽻻�SOAP��Ϣ
     * 
     * @throws Exception
     */
    @Test
    public void sendRequesetByPAYLOAD() throws Exception {
        //1��ƴ��payload
        String payload = "<tns:studentRequest xmlns:tns=\"http://www.example.org/student\"><Name>ʹ��SpringWS����������Լ���ȵ�WebService</Name><Subject1>1</Subject1><Subject2>3</Subject2><Subject3>5</Subject3></tns:studentRequest>";

        //2������wsdl URL��ַ
        url = new URL("http://localhost:8080/maven-framework/sws/services/studentDetails.wsdl");
        /*
         *3������Service
         *��һ������Ϊwsdl URL��ַ,
         *�ڶ�������WebService service��name StudentDetailsService �μ�wsdl�е�<wsdl:service name="StudentDetailsService"> 
         */
        Service service = Service.create(url, new QName(NAMESPACE_URI, "StudentDetailsService"));

        /*
         * 4������Dispatch
         * ��һ������ΪportName �μ�wsdl�е�<wsdl:port binding="tns:StudentDetailsSoap11" name="StudentDetailsSoap11">
         * �ڶ�������ΪSOAP��Ϣ���������͵� ��������ʹ��Source
         * ������������ָ��Ϣ�������������͵� MESSAGE provides access to entire protocol message, PAYLOAD to protocol message
         */
        Dispatch<Source> dispatch = service.createDispatch(new QName(NAMESPACE_URI,
                "StudentDetailsSoap11"), Source.class, Service.Mode.PAYLOAD);

        /*
         * 5��������Ϣdispatch.invoke(new StreamSource(new StringReader(payload)));
         */
        Source source = dispatch.invoke(new StreamSource(new StringReader(payload)));

        System.out.println("��Source��ʽƴ�ӵ���ϢΪ:" + payload);

        System.out.println("********************������Ϣ��*********************");
        //1������DOMResult
        DOMResult domResult = new DOMResult();
        //2����sourceת��ΪdomResult
        TransformerFactory.newInstance().newTransformer().transform(source, domResult);
        //3����ȡ�ڵ� ǿ��ת��ΪDocument ��ȡ����
        Document document = (Document) domResult.getNode();
        String name = document.getElementsByTagNameNS(NAMESPACE_URI, "Name").item(0)
                .getTextContent();
        String department = document.getElementsByTagNameNS(NAMESPACE_URI, "Department").item(0)
                .getTextContent();
        String subject1 = document.getElementsByTagNameNS(NAMESPACE_URI, "Subject1").item(0)
                .getTextContent();
        String subject2 = document.getElementsByTagNameNS(NAMESPACE_URI, "Subject2").item(0)
                .getTextContent();
        String subject3 = document.getElementsByTagNameNS(NAMESPACE_URI, "Subject3").item(0)
                .getTextContent();
        String total = document.getElementsByTagNameNS(NAMESPACE_URI, "Total").item(0)
                .getTextContent();

        System.out.println("\n�����������:\nName:" + name + "\nDepartment:" + department
                + "\nSubject1:" + subject1 + "\nSubject2:" + subject2 + "\nSubject3:" + subject3
                + "\nTotal:" + total);
    }

}
