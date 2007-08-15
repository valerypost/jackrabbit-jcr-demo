
	function addComment(itemID){
		var tt =itemID;
		var textObject = document.getElementById(itemID+'C');
		
		textObject.innerHTML = 
		"<div id='commentMessage"+itemID+"' align='right'> </div>                                                              " +
        "<form action='/jackrabbit-jcr-demo/blog/comment' method='GET' onsubmit='return validateForm(this,"+itemID+");'>       " +
        "  <br/> Leave your comment below <br/>                                                                                " + 
        "  <textarea cols='42' rows='4'	  name='comment'></textarea>			                                               " +
        "  <input name='action' type='hidden' id='action' value='comment'>                                                     " +
        "  <input name='UUID' type='hidden' id='UUID' value=" + itemID + ">                                                    " +
        "  <br/> <input type='submit' name='Button' value='Save'>                                                              " +
		"  <input type='reset' name='Submit2' value='Clear'>                                                                   " +
		"  <input type='button' name='Cancel' value='Cancel' onClick=\"removeComment('"+itemID+"');\">                         " +
        " </form>";
	}
	
	function  removeComment(itemID) {
		var tt = itemID+'C';
		var textObject = document.getElementById(tt);
		
		textObject.innerHTML = 
		"<input type='button' name='Button' value='Comment' onClick=\"addComment('"+itemID+"')\";/>";
	}
	
	function isNotEmpty(comment){
	
		with (comment)
		{
			if (value==null||value=="")
  			{
				return false;
			} else {
				return true;
			}
		}
	}
	
	function validateForm(form,count) {
		with (form) {
			
			
			var ne = isNotEmpty(commentArea);
			
			if (ne) {
				
				return true;
			} else {
				
				var tt ="commentMessage"+count;
				var textObject = document.getElementById(tt);
				textObject.innerHTML="<h4 align='center' style='color: #FF0000'>Comment is empty</h4>";
				return false;
			}
		}
	}
	
	