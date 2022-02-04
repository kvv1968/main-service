
$(function () {
    getAllTopics();
});

function getAllTopics() {
    $.ajax({
        url: '/user/lesson/topics/task',
        method: 'GET',
        dataType: 'json',
        success: function (data) {
            let ul = $("<ul></ul>");
            let li = "";
            for (const [key, value] of Object.entries(data)) {
                li += "<li><strong>" + key + "</strong>" + value + "</li>";
            }
            ul.html(li);
            $('#mapTopics').append(ul);
        }
    });
}
