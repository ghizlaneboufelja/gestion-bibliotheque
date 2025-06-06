
$('document').ready(function() {



    $('table #editButton').on('click', function (event) {
        event.preventDefault();


        var href = $(this).attr('href');

        $.get(href, function (categorie, status) {

            $('#editId').val(categorie.id_categorie);
            $('#editNom').val(categorie.nom);
            $('#editDescription').val(categorie.description
            );
        });


        $('#EditModal').modal();
    });
    $('table #detailsButton').on('click', function (event) {
        event.preventDefault();


        var href = $(this).attr('href');

        $.get(href, function (categorie, status) {

            $('#detailsId').val(categorie.id_categorie);
            $('#detailsNom').val(categorie.nom);
            $('#detailsDescription').val(categorie.description);
        });


        $('#detailsModal').modal();
    });

    $('table #deleteButton').on('click' ,function(event){
        event.preventDefault();


        var href = $(this).attr('href');

        $('#confirmDeleteButton').attr('href', href);

        $('#deleteModal').modal();


    });

});
