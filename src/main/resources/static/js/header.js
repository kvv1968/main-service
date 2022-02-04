let myProfile = $("#my-profile");

function getUser() {
    $.ajax({
        url: "/user/profile",
        method: "get",
        success: function (data) {
            user = data;
            let li = "";
            li += "<ul><li>username -> " + data.username + "</li>";
            li += "<li>password -> " + data.password + "</li>";
            li += "<li>firstName -> " + data.firstName + "</li>";
            li += "<li>lastName -> " + data.lastName + "</li>";
            li += "<li>email -> " + data.email + "</li>";
            li += "<li>phone -> " + data.phone + "</li>";
            li += "<li>date -> " + data.date + "</li></ul>" +
                "<br><a data-toggle='modal' data-target='#updateProfileUser' style='color: #0f7864' href='#'>Изменить данные в профиле</a>";

            loadProfile(data);
            myProfile.html(li);

        }
    });
}
function loadProfile(user) {
    $('input[name=id]').val(user.id);
    $('input[name=username]').val(user.username);
    $('input[name=password]').val(user.password);
    $('input[name=firstName]').val(user.firstName);
    $('input[name=lastName]').val(user.lastName);
    $('input[name=email]').val(user.email);
    $('input[name=phone]').val(user.phone);
    $('input[name=date]').val(user.date);
    $('input[name=enabled]').val(user.enabled);
    $('input[name=role]').val(user.role.role);
}

$('#userEditProfileForm').submit(function (event) {
    event.preventDefault();
    let user = $('#userEditProfileForm').serialize();
    $.ajax({
        url: '/user/users',
        method: 'put',
        async: false,
        data: user,
        success: function (data) {
            $("#editCloseProfileButton").click();

        }
    });
})
