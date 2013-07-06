

function startQuizTimer()
{
	var e = document.getElementById('quiz_start_time');
	var today=new Date();

	if(e) {
		var start_time = e.value;

		if(e.value == "-1") {
			start_time = today.getTime();
			e.value = start_time;
		}

		var elapsed_seconds = Math.round((today.getTime() - start_time)/1000);

		document.getElementById('clock_txt').innerHTML=elapsed_seconds + " seconds";
	} else {
		document.getElementById('clock_txt').innerHTML="UNKNOWN " + today.getTime();
	}
	t=setTimeout(function(){startQuizTimer();},500);
}

