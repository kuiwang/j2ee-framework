package com.fengjing.framework.stax;

import java.io.InputStream;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.stream.EventFilter;
import javax.xml.stream.FactoryConfigurationError;
import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import javax.xml.stream.XMLStreamWriter;
import javax.xml.stream.events.XMLEvent;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.TransformerFactoryConfigurationError;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathFactory;

import org.junit.Test;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

/**
 * ʹ��Stax����xml
 * 
 * @author scott
 *
 */
public class StaxXmlUtil {

  /**
   * ���ڹ��ķ�ʽ����XML
   */
  @Test
  public void cursorParseXML() throws Exception {
    XMLInputFactory factory = XMLInputFactory.newInstance();
    InputStream is = StaxXmlUtil.class.getResourceAsStream("books.xml");
    XMLStreamReader reader = factory.createXMLStreamReader(is);
    while (reader.hasNext()) {
      int type = reader.next();
      // ����ͬ�Ľڵ�����
      // ����ʼ�ڵ�,��<bookstore>,<book>
      if (type == XMLStreamConstants.START_ELEMENT) {
        String name = reader.getName().toString();
        System.out.println("<" + name + ">");
        // ��ӡ<book category="COOKING">�ڵ��еĵ�һ������categoryֵ"COOKING"
        if (name.equals("book")) {
          System.out.println("�ڵ�����ֵΪ" + reader.getAttributeName(0) + "="
              + reader.getAttributeValue(0));
        }
        if (name.equals("title")) {
          System.out.println("�鼮[" + reader.getElementText() + "]");
        }
        if (name.equals("price")) {
          System.out.println("�ļ۸�Ϊ[" + reader.getElementText() + "]");
        }
        // ��ӡ�ı��ڵ������,ע���xml��ǩ�еĿհײ���Ҳ�����ı��ڵ�..�����ı��ڵ���û��name��,���Բ���getName
      } else if (type == XMLStreamConstants.CHARACTERS) {
        System.out.println("�ı��ڵ�ֵ=[" + reader.getText().trim() + "]");
        // ��������ڵ�,��</book>,</title>
      } else if (type == XMLStreamConstants.END_ELEMENT) {
        System.out.println("</" + reader.getName() + ">");
      }
    }
    // ���������xml,�ǵùر�..��������ǰ�ر�,��ΪStax�Ƕ�һ�����ͽ���һ���
    is.close();
  }

  /**
   * ���ڵ���ģ�͵ķ�ʽ����XML
   */
  @Test
  public void iteratorParseXML() throws Exception {
    XMLInputFactory factory = XMLInputFactory.newInstance();
    InputStream is = StaxXmlUtil.class.getResourceAsStream("books.xml");
    XMLEventReader reader = factory.createXMLEventReader(is);
    int iteratorNum = 0; // ͳ�Ʊ����Ĵ���
    while (reader.hasNext()) {
      XMLEvent event = reader.nextEvent();
      if (event.isStartElement()) { // ͨ��XMLEvent�жϽڵ�����
        String name = event.asStartElement().getName().toString(); // ͨ��event.asxxxת���ڵ�
        if (name.equals("title")) {
          System.out.print("�鼮[" + reader.getElementText() + "]");
        }
        if (name.equals("price")) {
          System.out.println("�ļ۸�Ϊ[" + reader.getElementText() + "]");
        }
      }
      iteratorNum++;
    }
    is.close();
    System.out.println("���β�������XML�Ĵ����ϼ�Ϊ[" + iteratorNum + "]��");
  }

  /**
   * ����Filter�ķ�ʽ����XML
   * 
   * @see ������Ч�Ĺ��˵����ý��в����Ľڵ�,Ч�ʻ��һЩ
   */
  @Test
  public void filterParseXML() throws Exception {
    XMLInputFactory factory = XMLInputFactory.newInstance();
    InputStream is = StaxXmlUtil.class.getResourceAsStream("books.xml");
    XMLEventReader reader =
        factory.createFilteredReader(factory.createXMLEventReader(is), new EventFilter() {

          @Override
          public boolean accept(XMLEvent event) {
            if (event.isStartElement()) {
              String name = event.asStartElement().getName().toString();
              if (name.equals("title") || name.equals("price")) {
                return true; // ����true��ʾ����ʾ,false��ʾ����ʾ
              }
            }
            return false;
          }
        });
    int iteratorNum = 0; // ͳ�Ʊ����Ĵ���
    while (reader.hasNext()) {
      XMLEvent event = reader.nextEvent();
      if (event.isStartElement()) {
        String name = event.asStartElement().getName().toString();
        if (name.equals("title")) {
          System.out.print("�鼮[" + reader.getElementText() + "]");
        }
        if (name.equals("price")) {
          System.out.println("�ļ۸�Ϊ[" + reader.getElementText() + "]");
        }
      }
      iteratorNum++;
    }
    is.close();
    System.out.println("���β�������XML�Ĵ����ϼ�Ϊ[" + iteratorNum + "]��");
  }

  /**
   * ����XPath�ķ�ʽ����XML
   */
  @Test
  public void xpathParseXML() throws Exception {
    InputStream is = StaxXmlUtil.class.getResourceAsStream("books.xml");
    // �����ĵ��������
    DocumentBuilder db = DocumentBuilderFactory.newInstance().newDocumentBuilder();
    // ͨ��DocumentBuilder����doc���ĵ�����
    Document doc = db.parse(is);
    // ����XPath
    XPath xpath = XPathFactory.newInstance().newXPath();
    // ��һ����������XPath���ʽ,�ڶ����������ĵ�..�����ǲ�����������Ϊcategory="WEB"��<book>�ڵ�
    NodeList list =
        (NodeList) xpath.evaluate("//book[@category='WEB']", doc, XPathConstants.NODESET);
    for (int i = 0; i < list.getLength(); i++) {
      Element e = (Element) list.item(i);
      // ����<book>�ڵ���ֻ����һ��<title>�ڵ�,����������item(0)
      System.out.println(e.getElementsByTagName("title").item(0).getTextContent());
      System.out.println(e.getElementsByTagName("price").item(0).getTextContent());
    }
    is.close();
  }

  /**
   * ʹ��XMLStreamWriter����XML
   */
  @Test
  public void xmlStreamWriterXML() throws XMLStreamException, FactoryConfigurationError {
    XMLStreamWriter xsw = XMLOutputFactory.newInstance().createXMLStreamWriter(System.out);
    xsw.writeStartDocument("GBK", "1.0");

    String namespaceURI = "http://blog.csdn.net/jadyer";
    xsw.writeStartElement("pre", "uesr", namespaceURI); // ��д<pre:uesr>
    xsw.writeStartElement("pre", "id", namespaceURI); // ��д<pre:id>
    xsw.writeCharacters("1"); // ��д1
    xsw.writeEndElement(); // ��д</pre:id>

    xsw.writeStartElement("pre", "name", namespaceURI); // ��д<pre:name>
    xsw.writeCharacters("admin"); // ��дadmin
    xsw.writeEndElement();

    xsw.writeStartElement("pre", "age", namespaceURI); // ��д<pre:age>
    xsw.writeCharacters("18"); // ��д18
    xsw.writeEndElement();

    xsw.writeEndElement(); // ��д</pre:uesr>

    xsw.writeEndDocument(); // ��д<?xml version="1.0" encoding="GBK"?>

    xsw.flush();
    xsw.close();
  }

  /**
   * ʹ��Transformer����XML
   */
  @Test
  public void transformerModifyXML() throws Exception, TransformerFactoryConfigurationError {
    InputStream is = StaxXmlUtil.class.getResourceAsStream("books.xml");
    DocumentBuilder db = DocumentBuilderFactory.newInstance().newDocumentBuilder();
    Document doc = db.parse(is);
    XPath xpath = XPathFactory.newInstance().newXPath();
    // �������ݺ�title="Learning XML"��<book>�ڵ�
    NodeList list =
        (NodeList) xpath.evaluate("//book[title='Learning XML']", doc, XPathConstants.NODESET);
    // ��ȡ<price>�ڵ�,����ֻ��һ������������<book>�ڵ�,����д����list.item(0)
    Element e = (Element) (((Element) list.item(0)).getElementsByTagName("price").item(0));
    e.setTextContent("333.9");
    Transformer tran = TransformerFactory.newInstance().newTransformer();
    tran.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
    tran.setOutputProperty(OutputKeys.INDENT, "yes"); // ��<?xml
                                                      // version=...?>��<bookestore>�ڵ�֮�任��,Ĭ�ϲ�����
    // �޸Ľڵ�
    tran.transform(new DOMSource(doc), new StreamResult(System.out));
    is.close();
  }

}
