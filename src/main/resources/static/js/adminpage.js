

function techUserSendMail(){
    let randomUserName = Math.random().toString(36).slice(-5);
    let randomPassword = Math.random().toString(36).slice(-8);
    let subject = "Временный технический  User, время действия сутки.\r\n\r\n";
    let message = "{\"username\":\"" + randomUserName + "\", \r\n\r\n \"password\":\"" + randomPassword + "\"}";
    let email = $("#input-email").val();

    $.ajax({
        url: '/user/mail-send',
        method: 'POST',
        async: false,
        data: "subject=" + subject + "&email=" + email + "&message=" + message,
        success: function (data) {
            if (data === 'success'){
                alert(data);
            } else {
                // requestConfirmation(data);
                alert(data);
            }
            $("#close-modal").click();
        },
        error: function (error) {
            alert(error);
            $("#close-modal").click();
        }
    });

}
let modal = document.getElementById("myModal");
// Get the button that opens the modal
let btn = document.getElementById("myBtn");

// Get the <span> element that closes the modal
const span = document.getElementsByClassName("close")[0];

// When the user clicks the button, open the modal
btn.onclick = function() {
    modal.style.display = "block";
}

// When the user clicks on <span> (x), close the modal
span.onclick = function() {
    modal.style.display = "none";
}

// When the user clicks anywhere outside of the modal, close it
window.onclick = function(event) {
    if (event.target === modal) {
        modal.style.display = "none";
    }
}
$('#load-file').on("submit", function (event) {
    event.preventDefault();
    const $that = $(this),
        formFile = new FormData($that.get(0));
    $.ajax({
        url: '/api/file',
        method: 'post',
        processData: false,
        contentType: false,
        data: formFile,
        success: function (data){
            alert(data);
            $('.close').click();
        },
        error: function (error) {
            alert(error.toUpperCase());
            $('.close').click();
        }

    })
    $('.close').click();
});

function deleteAllTaskTemplate() {
    $.ajax({
        url: '/api/tasks/del',
        method: 'delete',
        async: false,
        success: function (data) {
            alert(data);
            location.reload();
        }
    });
}
function deleteAllUserTask(){
    $.ajax({
        url: '/api/user-tasks/del',
        method: 'delete',
        success: function (data) {
            alert(data);
        }
    });
}

// let modalLoad = document.getElementById("loadfile");
//
// let loadJavafile = document.getElementById("loadJavafile");
//
// loadJavafile.onclick = function() {
//     modal.style.display = "block";
// }
// $('#load-file_1').on("submit", function (event) {
//     event.preventDefault();
//
//     const $that = $(this),
//         formFile = new FormData($that.get(0));
//     $.ajax({
//         url: '/api/upload/java-file',
//         method: 'post',
//         // processData: false,
//         // contentType: false,
//         data: formFile,
//         success: function (data){
//             alert(data);
//             $('.close').click();
//         },
//         error: function (error) {
//             alert(error);
//             $('.close').click();
//         }
//     })
// });
