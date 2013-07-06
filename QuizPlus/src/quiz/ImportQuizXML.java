package quiz;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import quiz.QuizQuestion.QuestionTypes;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

public class ImportQuizXML {

	public static void main(String argv[]) {

		try {

			SAXParserFactory factory = SAXParserFactory.newInstance();
			SAXParser saxParser = factory.newSAXParser();

			DefaultHandler handler = new DefaultHandler() {

				String 	quiz_name;
				int 	q_num;
				String 	q_type;

				String Current_element;
				String current_text;

				boolean supported_element = false;

				QuizQuestion q;

				boolean preferred = false;



				public void startElement(String uri, String localName,String qName, 
						Attributes attributes) throws SAXException {

					Current_element = qName;

					// defaults:
					current_text = "";
					preferred = false;
				
					
					if(q == null) {
						if (qName.equalsIgnoreCase("question")) {
							supported_element = false;

							System.out.println("Start question :" + qName);

							String type = attributes.getValue("type");

							if(type.equals("question-response")) {
								q = new QuizQuestionQR();
								supported_element = true;
							} else if(type.equals("fill-in-blank")) {
								q = new QuizQuestionFIB();
								supported_element = true;
							} else if(type.equals("multiple-choice")) {
								supported_element = true;
								q = new QuizQuestionMC();
							} else if(type.equals("picture-reponse")) {
								q = new QuizQuestionPR();
							} else {
								q = null;
							}						
						} else {

						}
					} else {
						
						// Inside question

						// Test attributes
						if( qName.equalsIgnoreCase("option")) { 
							if(new String("answer").equalsIgnoreCase(attributes.getValue("answer"))) {
								preferred = true;
							}
						}
						
						if( qName.equalsIgnoreCase("answer")) { 
							if(new String("preferred").equalsIgnoreCase(attributes.getValue("preferred"))) {
								preferred = true;
							}
						}
						
						
					}
				}

				public void endElement(String uri, String localName, String qName) throws SAXException {

					if(supported_element) {
						// Can populate question
						if(		qName.equalsIgnoreCase("query") ||
								qName.equalsIgnoreCase("post")  ||
								qName.equalsIgnoreCase("pre")
								) {
							int index = q.getQuestionString().size();

							if(qName.equalsIgnoreCase("pre"))
								index = 0;

							q.setQuestionString(current_text, index);
						}

						if(qName.equalsIgnoreCase("answer")) {
							int index = q.getCorrectAnswer().size();

							if(preferred)
								index = 0;
							q.setCorrectAnswer(current_text, index);
						}
						
						if(qName.equalsIgnoreCase("option")) {
							int index = q.getMultipleChoice().size();
							
							if(preferred)
								q.setCorrectAnswer(String.format("%d",index), 0);
							
							q.setMultipleChoice(current_text, index);
						}
						
						if(qName.equalsIgnoreCase("image-location")) {
							q.setImageUrl(current_text);
						}
					}
							
					// Question ends
					if(qName.equalsIgnoreCase("question")) {
						if(supported_element)
							System.out.println(q);
						q = null;
					}
				}

				public void characters(char ch[], int start, int length) throws SAXException {
					current_text = new String(ch, start, length);
				}

			};

			saxParser.parse("c:\\1.xml", handler);

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}