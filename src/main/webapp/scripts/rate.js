
	function addRate(itemID){
		var tt =itemID+'R';
		var textObject = document.getElementById(tt);
		
		textObject.innerHTML = 
		"<table><tr><td align='left' >"+
		"<span class='title15' >  I would say  </span>" +
		"<a href='/jackrabbit-jcr-demo/blog/rate?rank=1&UUID="+itemID+"'>"+
        "<img src='/jackrabbit-jcr-demo/images/rank1.gif' title='Makes me sick' alt='Makes me sick' width='25' height='25'/></a>&nbsp;"+
		"<a href='/jackrabbit-jcr-demo/blog/rate?rank=2&UUID="+itemID+"'>"+
		"<img src='/jackrabbit-jcr-demo/images/rank2.gif' title='Hmm, poor' alt='Hmm, poor' width='25' height='25'/></a>&nbsp;"+
		"<a href='/jackrabbit-jcr-demo/blog/rate?rank=3&UUID="+itemID+"'>"+
		"<img src='/jackrabbit-jcr-demo/images/rank3.gif' title='Fairly good' alt='Fairly good' width='25' height='25'/></a>&nbsp;"+
		"<a href='/jackrabbit-jcr-demo/blog/rate?rank=4&UUID="+itemID+"'>"+
		"<img src='/jackrabbit-jcr-demo/images/rank4.gif' title='Kinda cool' alt='Kinda cool' width='25' height='25'/></a>&nbsp;"+
		"<a href='/jackrabbit-jcr-demo/blog/rate?rank=5&UUID="+itemID+"'>"+
		"<img src='/jackrabbit-jcr-demo/images/rank5.gif' title='It rocks' alt='It rocks' width='25' height='25'/></a>&nbsp;<br><br></td></tr>" +
		"<tr><td align='center'><input type='button' name='Cancel' value='Cancel' onClick=\"removeRate('"+itemID+"');\"></td></tr></table>";
		
	}

	function  removeRate(itemID) {
		var tt = itemID+'R';
		var textObject = document.getElementById(tt);
		
		textObject.innerHTML = 
		" <input type='button' name='Button' value='Rate' onClick=\"addRate('"+itemID+"');\"/>";
	}


	