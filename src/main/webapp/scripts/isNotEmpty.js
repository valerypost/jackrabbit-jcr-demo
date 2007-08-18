
function isEmpty() {
	
	var strfield1 = document.forms[0].changeNote.value; 

    if (strfield1 == "" || strfield1 == null )
    {
    	var textObject = document.getElementById('msgArea');
		textObject.innerHTML = "chanege note can't be empty ";
        return false;
    }
    
    return true;

}   