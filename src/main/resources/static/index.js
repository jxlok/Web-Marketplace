$(document).ready(function() {
    // Attach a click event handler to the filter checkboxes
    $('#filter-checkboxes input[type=checkbox]').click(function() {
        // Get the values of the checked checkboxes
        var filterValues = [];
        $('#filter-checkboxes input[type=checkbox]').each(function() {
            var value = $(this).val();
            if ($(this).is(':checked')) {
                filterValues.push(value + '=checked');
            } else {
                filterValues.push(value + '=unchecked');
            }
        });
        console.log(filterValues);
        var url = '/' + filterValues.join('/');

        // Send an AJAX request to the server to filter the items
        $.ajax({
            type: 'GET',
            url: url,
            success: function(data) {
                if (document.getElementById("item-list") == null) {
                    $('#block-list').html(data);
                } else {
                    var parent = document.getElementById("item-list").parentNode;
                    // Get a reference to the element you want to remove
                    var element = document.getElementById("item-list");
                    parent.removeChild(element);
                    $('#block-list').html(data);
                }

            },
            error: function(xhr, status, error) {
                console.log('Error:', error);
            }
        });
    });
});