/**
 * 
 */

 function showMyImage(fileInput,imageId) {
	 checkFileSize(fileInput);
	 if(fileInput != null)
	 {
        var files = fileInput.files;
        for (var i = 0; i < files.length; i++) {           
            var file = files[i];
            var imageType = /image.*/;     
            if (!file.type.match(imageType)) {
                continue;
            }           
            var img=document.getElementById(imageId);
            img.file = file;    
            var reader = new FileReader();
            reader.onload = (function(aImg) { 
                return function(e) { 
                    aImg.src = e.target.result; 
                }; 
            })(img);
            reader.readAsDataURL(file);
        }
	 }
    }
 
 function checkFileSize(inputFile) {
	    var max = 1 * 1024 * 1024; // 1MB

	    if (inputFile.files && inputFile.files[0].size > max) {
	        alert("El archivo es demasiado grande. \n Maximo = 1Mb"); // Do your thing to handle the error.
	        inputFile.value = null; // Clears the field.
	    }
	}