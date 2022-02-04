let contactForm = $("#contactForm");

contactForm.validator().on("submit", function (event) {
    if (event.isDefaultPrevented()) {
        // handle the invalid form...
        formError();
        submitMSG(false, "Did you fill in the form properly?");
    } else {
        // everything looks good!
        event.preventDefault();
        submitForm();
    }
});


function submitForm(){
    // // Initiate Variables With Form Content
    let name = $("#name").val();
    let email = $("#email").val();
    let message = $("#message").val();



    $.ajax({
        url: "/user/mail-send",
        method: "POST",
        data: "subject=" + name + "&email=" + email + "&message=" + message,
        success : function(text){
            if (text === "success"){
                formSuccess();
            } else {
                formError();
                submitMSG(false,text);
            }
        }
    });
}

function formSuccess(){
    contactForm[0].reset();
    submitMSG(true, "Message Submitted!")
}

function formError(){
    contactForm[0].reset();
    contactForm.removeClass().addClass('shake animated').one('webkitAnimationEnd mozAnimationEnd MSAnimationEnd oanimationend animationend', function(){
        $(this).removeClass();
    });
}

function submitMSG(valid, msg){
    let msgClasses = "";
    if(valid){
       msgClasses = "h3 text-center tada animated text-success";
    } else {
        msgClasses = "h3 text-center text-danger";
    }
    $("#msgSubmit").removeClass().addClass(msgClasses).text(msg);
}