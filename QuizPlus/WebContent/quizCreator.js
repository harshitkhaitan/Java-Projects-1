var next_question_idx = 0;
var valid_questions = 0;
var idx_ordinal_map = new Array();
var idx_type_map = new Array();

var default_status_div_name = "QuizStatus";

function removeElement(parentDiv, childDiv, num){
	if (childDiv == parentDiv) {
		;
	}
	else if (document.getElementById(childDiv)) {     
		var child = document.getElementById(childDiv);
		var parent = document.getElementById(parentDiv);
		parent.removeChild(child);
		valid_questions -= 1;
		idx_ordinal_map[num] = -1;

		updateQuestionOrdinals();

		// Last operation updates status div		
		updateStatus(default_status_div_name);
	}
	else {
		return false;
	}
}

function addElement(parentDiv){

	var parent = document.getElementById(parentDiv);
	if (parent) {     
		makeQuestionDiv(next_question_idx, parent);

		// Last operation updates status div
		updateStatus(default_status_div_name);
	} else {
		
		return false;
	}
}

function updateQuestionOrdinals() {
	var i;
	var question_ord = 0;

	for(i = 0; i < next_question_idx; i++) {

		if(idx_ordinal_map[i] < 0) {
			;
			//	Do nothing
		} else {
			idx_ordinal_map[i] = question_ord;
			var this_id = "Question_Oridnal_"+getQuestionTag(i);
			var ordinal_element = document.getElementById(this_id);
			ordinal_element.innerHTML = question_ord;
			question_ord += 1;
		}
	}
}


function updateQuestionDiv(num, q_div, create_new) {

	var question_id = getQuestionTag(num);
	var question_num = idx_ordinal_map[num];
	var question_name = "Question Number " + num;

	q_div = document.getElementById(question_id);
	
	var question_form = "";
	question_form += "<p>";
	question_form += "#";
	question_form += "<b id=Question_Oridnal_"+question_id+" >" + question_num + "</b>";
	
	question_form += "    Type: ";
	
	if(!create_new) {
		question_form += makeQuestionTypeTable(question_id, num, document.main_form.elements[question_id+"_type"].value);	
	} else {
		question_form += makeQuestionTypeTable(question_id, num, "QR");
	}
	
	question_form += "<input type=\"button\" value=\"Remove\" onClick=\"removeElement('QuizQuestions', '"+question_id+"', "+num+")\" >";
	
	question_form += "</p>";

	if(!create_new) {
		switch (document.main_form.elements[question_id+"_type"].value) {		
		case "QR":
			question_form += makeQuestionQR(num);
			idx_type_map[num] = "QR";
			break;

		case "FIB":
			question_form += makeQuestionFIB(num);
			idx_type_map[num] = "FIB";
			break;
			
		case "MC":
			question_form += makeQuestionMC(num);
			idx_type_map[num] = "MC";
			break;
			
		case "PR":
			question_form += makeQuestionPR(num);
			idx_type_map[num] = "PR";
			break;

			
		default:
			question_form += "<p>Unknown Question Type</p>";
		break;
		}
	} else {
		//idx_type_map[num] = "NONE";
		question_form += makeQuestionQR(num);
		idx_type_map[num] = "QR";
	}

	if(q_div) {
		q_div.innerHTML = question_form;
		q_div.setAttribute('class', 'question_div');
	} else {
		alert ("Cannot update question form!! id=" + question_id);
	}
}

function makeQuestionDiv(num, question_parent) {
	var new_question_id = getQuestionTag(num);
	var new_question = document.createElement('div');
	new_question.setAttribute('id', new_question_id);
	idx_ordinal_map[next_question_idx] = valid_questions;


	question_parent.appendChild(new_question, next_question_idx);

	updateQuestionDiv(num, new_question, true);

	next_question_idx += 1;
	valid_questions += 1;


	return new_question;

}




function makeQuestionTypeTable (id,num, selected) {
	var question_type_form = "";
	var mark_selected = "";
	
	question_type_form += "<select name=|$qid_type| onchange=|updateQuestionDiv('$qn', false, false)|>";
	//question_type_form += "  <option value=|| style=|display:none;|></option>";
	
	if(selected == "QR") { mark_selected = "selected"; } else {mark_selected = "";}
	question_type_form += "  <option value=|QR|  "+mark_selected+">Question Response</option>";
	
	if(selected == "FIB") { mark_selected = "selected"; } else {mark_selected = "";}
	question_type_form += "  <option value=|FIB| "+mark_selected+">Fill In The Blank</option>";
	
	if(selected == "MC") { mark_selected = "selected"; } else {mark_selected = "";}
	question_type_form += "  <option value=|MC|  "+mark_selected+">Multiple Choice</option>";
	
	if(selected == "PR") { mark_selected = "selected"; } else {mark_selected = "";}
	question_type_form += "  <option value=|PR|  "+mark_selected+">Picture Response</option>";
	
	question_type_form += "</select>";

	question_type_form = substituteVars(question_type_form, id, num);
	return question_type_form;
}


function getQuestionTag (num) {
	return "Question_Div_" + num;
}

function updateStatus (status_div_name) {
	var status_div = document.getElementById(status_div_name);

	var new_html = "";

	//new_html += "<p>";
	new_html += "Question Count: "+valid_questions;
	//new_html += "</p>";

	new_html +="<input name=\"valid_questions\" type=\"hidden\" value=\""+valid_questions+"\"/>";
	new_html +="<input name=\"question_number\" type=\"hidden\" value=\""+next_question_idx+"\"/>";

	status_div.innerHTML = new_html;

	if(valid_questions > 0) {
		document.main_form.SubmitNewQuizQuestions.disabled=false;
	} else {
		document.main_form.SubmitNewQuizQuestions.disabled=true;
	}
}

function substituteVars(input_str, id, num) {
	input_str = input_str.replace(/\|/g, "\"");
	input_str = input_str.replace(/\$qid/g, id);
	input_str = input_str.replace(/\$qn/g, num);
	return input_str;
}

function makeQuestionQR (num) {
	var qid = getQuestionTag(num);

	var qr_form = "" +
	"<table class=\"invisible\" >" +

	"<tr>" +
	"<td> Question:" +
	"<td> <textarea cols=|50| rows=|3| name=$qid_Question >" +
	"Your question goes here" +
	"</textarea>" +

	"<tr>" +
	"<td> Answer:<br>One per line" +
	"<td> <textarea cols=|50| rows=|3| name=|$qid_Answer|></textarea>" +
	// "<td> <input type=|text| size=|50| name=|$qid_Answer| value=|your answer goes here| />" +
	"</table>" +
	"";	
	qr_form = substituteVars(qr_form,qid,num);

	return qr_form;
}

function makeQuestionFIB (num) {
	var qid = getQuestionTag(num);

	var qr_form = "" +
	"<table class=\"invisible\" >" +

	"<tr>" +
	"<td> Question:" +
	"<td> <textarea cols=|25| rows=|3| name=$qid_Question_0 >" +
	"Your question goes here" +
	"</textarea>" +
	"<b> __________ <b>" +
	"<textarea cols=|25| rows=|3| name=$qid_Question_1 >" +
	"Your question goes here" +
	"</textarea>" +

	"<tr>" +
	"<td> Answer:<br>One per line" +
	"<td> <textarea cols=|50| rows=|3| name=|$qid_Answer|></textarea>" +
	//"<td> <input type=|text| size=|50| name=|$qid_Answer| value=|your answer goes here| />" +
	"</table>" +
	"";	
	qr_form = substituteVars(qr_form,qid,num);

	return qr_form;
}


function makeQuestionMC (num) {
	var qr_form = "";
	var qid = getQuestionTag(num);
	var update_tag = 0;
	var mc_length = 0;

	var this_length_tag = qid + "_MC_ChoiceCount";

	var mc_length_element = document.getElementById(this_length_tag);

	if(mc_length_element) {
		update_tag = 1;
		mc_length = mc_length_element.value;
	} else {
		update_tag = 0;
		mc_length = 1;
		qr_form += "<input name=|$qid_MC_ChoiceCount| id=|$qid_MC_ChoiceCount| type=|hidden| value=|1|/>";
	}

	var table_tag = qid + "_ChoiceText_Table";
	qr_form += "" 
		+"<table class=\"invisible\" >"
		+"<tr>"
		+"<td> Question:"
		+"<td> <textarea cols=|50| rows=|3| name=$qid_Question >"
		+"Your question goes here"
		+"</textarea>"
		+"<tr>"
		+"<td> Choices:"
		+"<td>"
		+"<table id=" +table_tag+ "  class=\"invisible\" >";

	for(var i = 0; i < mc_length; i++) {
		var this_tag = "$qid_MC_idx"+i+"_ChoiceText";
		qr_form += ""
			+"<tr>"
			//+"<td>"
			+"<td><input type=|radio| name=|$qid_Answer| value=|"+i+"| >"
			+"<td><input type=|text| size=|50| name=|" +this_tag+ "| value=|your answer goes here| >";

		if(i == 0) {
			qr_form += "<td><button value=|| type=|button| onclick=|addMCChoice('$qid', '" +table_tag+ "', '" +this_tag+ "', " +i+ ")| name=|Add Option|>+</button>";
			qr_form += "<td><button value=|| type=|button| onclick=|removeMCChoice('$qid', '" +table_tag+ "', '" +this_tag+ "', " +i+ ")| name=|Add Option|>-</button> (Upto 10)";
		} else {
			qr_form += "<td>";
			qr_form += "<td>";
		}
	}

	qr_form += "" 
		+"</table>"
		+"<tr>"
		+"<td> Answer:"
		+"<td> (Select option from list above)"
		+"</table>"
		+"";	

	qr_form = substituteVars(qr_form,qid,num);

	return qr_form;
}


function makeQuestionPR (num) {
	
	var qid = getQuestionTag(num);

	var qr_form = "" +
	"<table class=\"invisible\" >" +

	"<tr>" +
	"<td> Question:" +
	"<td> <textarea cols=|50| rows=|3| name=$qid_Question >" +
	"Your question goes here" +
	"</textarea>" +
	"<td rowspan=|3| style=|width:500px; text-align:center;|><img src=|images/whitespace.gif| id=|$qid_IMG_id| style=|max-width:200px; max-height:200px;| />" +

	
	"<tr> " +
	"<td> Image URL:" +
	"<td> <input type=|text| size=|50| id=|$qid_URL_id| name=|$qid_URL| value=|your answer goes here| />" +
	
	"<tr>" +
	"<td>" +
	"<td style=|width:500px; text-align:center;|><button value=|Preview Image| type=|button| onclick=|previewURL('$qid_URL_id', '$qid_IMG_id')|>Preview Image</button>" +
	
	"<tr>" +
	"<td> Answer: One per line" +
	"<td> <textarea cols=|50| rows=|3| name=|$qid_Answer|></textarea>" +
	//"<td> <input type=|text| size=|50| name=|$qid_Answer| value=|your answer goes here| />" +
	"</table>" +
	"";	
	qr_form = substituteVars(qr_form,qid,num);

	return qr_form;	
}
	

function removeMCChoice (qid, table_tag, this_tag, row) {
	var mc_table = document.getElementById(table_tag);

	var this_tag = qid+ "_MC_idx"+row+"_ChoiceText";
	var this_length_tag = qid+ "_MC_ChoiceCount";

	var mc_length_element = document.getElementById(this_length_tag);
	var mc_length = mc_length_element.value;

	if(mc_table.rows.length > 1) {
		mc_table.deleteRow(mc_table.rows.length-1);
		mc_length_element.value = mc_table.rows.length;
	}
}


function previewURL (url_box_tag, preview_image_tag) {
	var url_box = document.getElementById(url_box_tag);
	var preview_image = document.getElementById(preview_image_tag);

	
	preview_image.src = url_box.value;
}

function addMCChoice (qid, table_tag, this_tag, row) {
	var mc_table = document.getElementById(table_tag);

	var this_length_tag = qid+ "_MC_ChoiceCount";

	var mc_length_element = document.getElementById(this_length_tag);
	var mc_length = mc_length_element.value;
	var this_tag = qid+ "_MC_idx"+mc_length+"_ChoiceText";

	if(mc_length < 10) {
		var new_row = mc_table.insertRow(mc_table.rows.length);
	
	
		var ord     = new_row.insertCell(0);
		var textbox = new_row.insertCell(1);
		var addbutton = new_row.insertCell(2);	
		var removebutton = new_row.insertCell(3);
	
		ord.innerHTML 		= substituteVars("<input type=|radio| name=|$qid_Answer| value=|"+(mc_table.rows.length - 1)+"| >", qid, -1);
		textbox.innerHTML 	= substituteVars("<input type=|text| size=|50| name=|" +this_tag+ "| value=|your answer goes here| >", qid, -1);
		addbutton.innerHTML	= "";
		removebutton.innerHTML	= "";
	
		mc_length_element.value = mc_table.rows.length;
	}
}

function prepForSubmit () {
	var status_div = document.getElementById(default_status_div_name);

	var idx_ordinal_map_asString = "";
	var idx_type_map_asString = "";
		
	for(var i = 0; i < next_question_idx; i++) {
		if(i > 0) {
			idx_ordinal_map_asString += ",";
			idx_type_map_asString += ",";
		}
		idx_ordinal_map_asString += idx_ordinal_map[i];
		idx_type_map_asString += idx_type_map[i];
		
		
	}

	status_div.innerHTML += "<input name=\"idx_ordinal_map_asString\" type=\"hidden\" value=\""+idx_ordinal_map_asString+"\" />";
	status_div.innerHTML += "<input name=\"idx_type_map_asString\" type=\"hidden\" value=\""+idx_type_map_asString+"\" />";
	
	//alert("Returning "+ idx_type_map_asString + " " + idx_ordinal_map_asString);
}
