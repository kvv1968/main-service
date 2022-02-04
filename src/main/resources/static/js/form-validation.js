let principal = $('#principal');

$(function () {

});




(function () {
    'use strict'

    // Fetch all the forms we want to apply custom Bootstrap validation styles to
    let forms = document.querySelectorAll('.needs-validation')

    // Loop over them and prevent submission
    Array.prototype.slice.call(forms)
        .forEach(function (form) {
            form.addEventListener('submit', function (event) {
                if (!form.checkValidity()) {
                    event.preventDefault()
                    event.stopPropagation()
                }

                form.classList.add('was-validated')
            }, false)
        })
})()

$('#newFormRegUser').on('submit', function (event) {
    event.preventDefault();
    let user = $('#newFormRegUser').serialize();
    $.ajax({
        url: '/user/form-reg',
        method: 'post',
        data: user,
        success: function (data) {
            $('#buttonModal').click();
            let modal = $('.modal-body');
            let div = "<div><ul>" +
                "<li>" + "Логин - " + data.username + "</li>" +
                "<li>" + "Пароль - " + data.password + "</li>" +
                "<li>" + "Имя - " + data.firstName + "</li>" +
                "<li>" + "Фамилия - " + data.lastName + "</li>" +
                "<li>" + "Email - " + data.email + "</li>" +
                "<li>" + "Телефон - " + data.phone + "</li>" +
                "<li>" + "Дата регистрации - " + new Date() + "</li>" +
                "</ul></div>";

            modal.html(div);
            let mod = $('#exampleModal');
            mod.on('hide.bs.modal', function () {
                document.location.href="/user/nav";
            })
        }
    });
});

