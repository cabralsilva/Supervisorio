$(document).ready(function() {
	tableCustom = $('#tableRelatorioZoneTime').DataTable( {
    	"order": [],
    	"paging": false,
    	"bInfo" : false,
    	"searching": false,
    	 dom: 'Bfrtip',
         buttons: [
             'excelHtml5',
             'csvHtml5',
             'pdfHtml5'
         ]
    } );
} );


