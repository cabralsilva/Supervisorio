/*!
 * remark (http://getbootstrapadmin.com/remark)
 * Copyright 2015 amazingsurge
 * Licensed under the Themeforest Standard Licenses
 */
(function(document, window, $) {
  'use strict';

  var Site = window.Site;
  var action = "save";
  var idAction = "-1";
  $(document).ready(function($) {
    Site.run();
  });

  // Fixed Header Example
  // --------------------
  (function() {
    var offsetTop = 0;
    if ($('.site-navbar').length > 0) {
      offsetTop = $('.site-navbar').eq(0).innerHeight();
    }

    // initialize datatable
    var table = $('#exampleFixedHeader').DataTable({
      responsive: true,
      fixedHeader: {
        header: true,
        headerOffset: offsetTop
      },
      "bPaginate": false,
      "sDom": "t" // just show table, no other controls
    });



    // redraw fixedHeaders as necessary
    // $(window).resize(function() {
    //   fixedHeader._fnUpdateClones(true);
    //   fixedHeader._fnUpdatePositions();
    // });
  })();

  // Individual column searching
  // ---------------------------
  (function() {
    $(document).ready(function() {
      var defaults = $.components.getDefaults("dataTable");

      var options = $.extend(true, {}, defaults, {
        initComplete: function() {
          this.api().columns().every(function() {
            var column = this;
            var select = $('<select class="form-control width-full"><option value=""></option></select>')
              .appendTo($(column.footer()).empty())
              .on('change', function() {
                var val = $.fn.dataTable.util.escapeRegex(
                  $(this).val()
                );

                column
                  .search(val ? '^' + val + '$' : '', true, false)
                  .draw();
              });

            column.data().unique().sort().each(function(d, j) {
              select.append('<option value="' + d + '">' + d + '</option>')
            });
          });
        }
      });

      $('#exampleTableSearch').DataTable(options);
    });
  })();

  // Table Tools
  // -----------
  (function() {

    $(document).ready(function() {
      var defaults = $.components.getDefaults("dataTable");

      var options = $.extend(true, {}, defaults, {
        "aoColumnDefs": [{
          'bSortable': false,
          'aTargets': [-1]
        }],
        "iDisplayLength": 5,
        "aLengthMenu": [
          [5, 10, 25, 50, -1],
          [5, 10, 25, 50, "All"]
        ],
        "sDom": '<"dt-panelmenu clearfix"Tfr>t<"dt-panelfooter clearfix"ip>',
        "oTableTools": {
          "sSwfPath": "../../../global/vendor/datatables-tabletools/swf/copy_csv_xls_pdf.swf"
        }
      });

      $('#exampleTableTools').dataTable(options);
    });
  })();

  // Table Add Row
  // -------------
  (function($) {
    var EditableTable = {

      options: {
        addButton: '#addToTable',
        table: '#exampleAddRow',
        dialog: {
          wrapper: '#dialog',
          cancelButton: '#dialogCancel',
          confirmButton: '#dialogConfirm',
        }
      },

      initialize: function() {
        this
          .setVars()
          .build()
          .events();
      },

      setVars: function() {
        this.$table = $(this.options.table);
        this.$addButton = $(this.options.addButton);

        // dialog
        this.dialog = {};
        this.dialog.$wrapper = $(this.options.dialog.wrapper);
        this.dialog.$cancel = $(this.options.dialog.cancelButton);
        this.dialog.$confirm = $(this.options.dialog.confirmButton);

        return this;
      },

      build: function() {
        this.datatable = this.$table.DataTable({
          aoColumns: [
            null,
			null,
			null,
			null,
			null,
			null,
			null,
			null,
			null,
			null,
			null,
			null,
			null,
			null,
		    {
              "bSortable": false
            }
          ],
          language: {
            "sSearchPlaceholder": "Search..",
            "lengthMenu": "_MENU_",
            "search": "_INPUT_"
          }
        });

        window.dt = this.datatable;

        return this;
      },

      events: function() {
        var _self = this;

        this.$table
          .on('click', 'a.save-row', function(e) {
            e.preventDefault();
			
            _self.rowSave($(this).closest('tr'));
          })
          .on('click', 'a.cancel-row', function(e) {
            e.preventDefault();
			action = "cancel";
            _self.rowCancel($(this).closest('tr'));
          })
          .on('click', 'a.edit-row', function(e) {
            e.preventDefault();
			action = "edit";
            _self.rowEdit($(this).closest('tr'));
          })
          .on('click', 'a.remove-row', function(e) {
            e.preventDefault();
			action = "delete";
            var $row = $(this).closest('tr');
            bootbox.dialog({
              message: "Tem certeza de que deseja excluir este item?",
              title: "VOCE TEM CERTEZA?",
              buttons: {
                danger: {
                  label: "SIM",
                  className: "btn-danger",
                  callback: function() {
                    _self.rowRemove($row);
                  }
                },
                main: {
                  label: "NÃO",
                  className: "btn-primary",
                  callback: function() {}
                }
              }
            });
          });

        this.$addButton.on('click', function(e) {
          e.preventDefault();
			action = "save";
          _self.rowAdd();
        });

        this.dialog.$cancel.on('click', function(e) {
          e.preventDefault();
          $.magnificPopup.close();
        });

        return this;
      },


      // =============
      // ROW FUNCTIONS
      // =============
      rowAdd: function() {
		 
        this.$addButton.attr({
          'disabled': 'disabled'
        });

        var actions,
          data,
          $row;

        actions = [
          '<a href="#" class="btn btn-sm btn-icon btn-pure btn-default hidden on-editing save-row" data-toggle="tooltip" data-original-title="Save"><i class="icon wb-wrench" aria-hidden="true"></i></a>',
          '<a href="#" class="btn btn-sm btn-icon btn-pure btn-default hidden on-editing cancel-row" data-toggle="tooltip" data-original-title="Delete"><i class="icon wb-close" aria-hidden="true"></i></a>',
          '<a href="#" class="btn btn-sm btn-icon btn-pure btn-default on-default edit-row" data-toggle="tooltip" data-original-title="Edit"><i class="icon wb-edit" aria-hidden="true"></i></a>',
          '<a href="#" class="btn btn-sm btn-icon btn-pure btn-default on-default remove-row" data-toggle="tooltip" data-original-title="Remove"><i class="icon wb-trash" aria-hidden="true"></i></a>'
        ].join(' ');

        data = this.datatable.row.add(['', '', '', '', '', '', '0', '', '', '', '', '', '', '', actions]);
        $row = this.datatable.row(data[0]).nodes().to$();

        $row
          .addClass('adding')
          .find('td:last')
          .addClass('actions');;
        this.rowEdit($row);
        this.datatable.order([0, 'asc']).draw(); // always show fields
      },

      rowCancel: function($row) {
        var _self = this,
          $actions,
          i,
          data;

        if ($row.hasClass('adding')) {
          this.rowRemove($row);
        } else {

          data = this.datatable.row($row.get(0)).data();
          this.datatable.row($row.get(0)).data(data);

          $actions = $row.find('td.actions');
          if ($actions.get(0)) {
            this.rowSetActionsDefault($row);
          }

          this.datatable.draw();
        }
      },

      rowEdit: function($row) {
        var _self = this,
          data;

        data = this.datatable.row($row.get(0)).data();
		var valueAddRota;
		var valueTag;
		var valueSetPoint;
		var valueVelocidade;
		var valueGirar;
		var valueEstadoAtuador;
		var valueSensorObstaculo;
		var valueSinalSonoro;
		var valueTagDestino;
		var valueTagParada;
		var valuePitStop
		
        $row.children('td').each(function(i) {
          var $this = $(this);
	 
          if ($this.hasClass('actions')) {
            _self.rowSetActionsEditing($row);
          } else {
			if(i == 0) { 
			  $this.html('<input type="text" id="' + i + '" class="form-control input-block" value="' + data[i] + '"/>');
              idAction = data[i];
			} else if(i == 1) {
			  $this.html('<input type="number" id="' + i + '" class="form-control input-block" value="' + data[i] + '"/>');
   			} else if(i == 2) {
			  $this.html('<select  id="selectAddRota" class="form-control" data-plugin="select">' + rowComboboxRotas() + '</select>');
              valueAddRota = data[i];
			} else if(i == 3) {
			  $this.html('<select  id="selectTag" class="form-control" data-plugin="select">' + rowComboboxTags() + '</select>');
              valueTag = data[i];
			} else if(i == 4) {
			  $this.html('<select  id="selectSetPoint" class="form-control" data-plugin="select">' + rowComboboxSetPoint() + '</select>');
              valueSetPoint = data[i];
			} else if(i == 5) {
			  $this.html('<select  id="selectVelocidade" class="form-control" data-plugin="select">' + rowComboboxVelocidade() + '</select>');
              valueVelocidade = data[i];
			} else if(i == 6) {
			  $this.html('<input type="number" id="' + i + '" class="form-control input-block" value="' + data[i] + '"/>');
			} else if(i == 7) {
			  $this.html('<select  id="selectGirar" class="form-control" data-plugin="select">' + rowComboboxGirar() + '</select>');
              valueGirar = data[i];
			} else if(i == 8) {
			  $this.html('<select  id="selectEstadoAtuador" class="form-control" data-plugin="select">' + rowComboboxEstadoAtuador() + '</select>');
              valueEstadoAtuador = data[i];
			} else if(i == 9) {
			  $this.html('<select  id="selectSensorObstaculo" class="form-control" data-plugin="select">' + rowComboboxSensorObstaculo() + '</select>');
              valueSensorObstaculo = data[i];
			} else if(i == 10) {
			  $this.html('<select  id="selectSinalSonoro" class="form-control" data-plugin="select">' + rowComboboxSinalSonoro() + '</select>');
              valueSinalSonoro = data[i];
			} else if(i == 11) {
			  $this.html('<select  id="selectTagDestino" class="form-control" data-plugin="select">' + rowComboboxTagDestino() + '</select>');
              valueTagDestino = data[i];
			} else if(i == 12) {
			  $this.html('<select  id="selectTagParada" class="form-control" data-plugin="select">' + rowComboboxTagParada() + '</select>');
              valueTagParada = data[i];
			} else if(i == 13) {
			  $this.html('<select  id="selectPitStop" class="form-control" data-plugin="select">' + rowComboboxPitStop() + '</select>');
              valuePitStop = data[i];
			} else {
              $this.html('<input type="text" id="' + i + '" class="form-control input-block" value="' + data[i] + '"/>');
            }
          }
        });
		

		var element1 = document.getElementById('selectAddRota');
		if(valueAddRota != '') {
			element1.value = valueAddRota;
			if(valueAddRota == 'Desativado') {
				element1.value = 'null';
			}
		}
		var element2 = document.getElementById('selectTag');
		if(valueTag != '') {
			var i;
			for(i = 0; i < element2.childNodes.length; i++) {
				if(element2.childNodes[i].text == valueTag) {
					element2.value = element2.childNodes[i].value;
				}
			}

		}
		var element3 = document.getElementById('selectSetPoint');
		if(valueSetPoint != '')
			element3.value = valueSetPoint;
		var element4 = document.getElementById('selectVelocidade');
		if(valueVelocidade != '')
			element4.value = valueVelocidade;
		var element5 = document.getElementById('selectGirar');
		if(valueGirar != '')
			element5.value = valueGirar;
		var element6 = document.getElementById('selectEstadoAtuador');
		if(valueEstadoAtuador != '')
			element6.value = valueEstadoAtuador;
		var element7 = document.getElementById('selectSensorObstaculo');
		if(valueSensorObstaculo != '')
			element7.value = valueSensorObstaculo;
		var element8 = document.getElementById('selectTagDestino');
		if(valueTagDestino != '')
			element8.value = 0;
			if(valueTagDestino == 'Ativado'){
				element8.value = 1;
			}
		var element9 = document.getElementById('selectTagParada');
		if(valueTagParada != '') {
			element9.value = 0;
			if(valueTagParada == 'Ativado'){
				element9.value = 1;
			}
		}
		var element10 = document.getElementById('selectPitStop');
		if(valuePitStop != '')
			element10.value = 0;
			if(valuePitStop == 'Ativado'){
				element10.value = 1;
			}
		var element11 = document.getElementById('selectSinalSonoro');
		if(valueSinalSonoro != '')
			element11.value = valueSinalSonoro;
      },

      rowSave: function($row) {


	var link = '/ActionCadastroRotasTags?action=' + action + '&idOld=' + idAction + '&nome=' + document.getElementById("0").value + '&posicao=' + document.getElementById("1").value + '&addRota=' + document.getElementById("selectAddRota").value + '&tag=' + document.getElementById("selectTag").value + '&setPoint=' + document.getElementById("selectSetPoint").value + '&velocidade=' + document.getElementById("selectVelocidade").value + '&comando=' + document.getElementById("6").value + '&girar=' + document.getElementById("selectGirar").value + '&atuador=' + document.getElementById("selectEstadoAtuador").value + '&sensorObstaculo=' + document.getElementById("selectSensorObstaculo").value + '&sinalSonoro=' + document.getElementById("selectSinalSonoro").value + '&tagDestino=' + document.getElementById("selectTagDestino").value + '&tagParada=' + document.getElementById("selectTagParada").value + '&pitStop=' + document.getElementById("selectPitStop").value + '&rota=' + nameRota();
	if(enviarDados(link)){
        var _self = this,
          $actions,
          values = [];

        if ($row.hasClass('adding')) {
          this.$addButton.removeAttr('disabled');
          $row.removeClass('adding');
        }

		var i = 0;
        values = $row.find('td').map(function() {
          var $this = $(this);
		  i++;
          if ($this.hasClass('actions')) {
             _self.rowSetActionsDefault($row);
            return _self.datatable.cell(this).data();
		  } else {
			var e1;
			
			if(i == 3) {
				e1 = document.getElementById("selectAddRota");
				return e1.options[e1.selectedIndex].text;
			}
			
			if(i == 4) {
				e1 = document.getElementById("selectTag");
				return e1.options[e1.selectedIndex].text;
			}
			
			if(i == 5) {
				e1 = document.getElementById("selectSetPoint");
				return e1.options[e1.selectedIndex].text;
			}
			
			if(i == 6) {
				e1 = document.getElementById("selectVelocidade");
				return e1.options[e1.selectedIndex].text;
			}
			
			if(i == 8) {
				e1 = document.getElementById("selectGirar");
				return e1.options[e1.selectedIndex].text;
			}
			
			if(i == 9) {
				e1 = document.getElementById("selectEstadoAtuador");
				return e1.options[e1.selectedIndex].text;
			}
			
			if(i == 10) {
				e1 = document.getElementById("selectSensorObstaculo");
				return e1.options[e1.selectedIndex].text;
			}
			
			if(i == 11) {
				e1 = document.getElementById("selectSinalSonoro");
				return e1.options[e1.selectedIndex].text;
			}
			
			if(i == 12) {
				e1 = document.getElementById("selectTagDestino");
				return e1.options[e1.selectedIndex].text;
			}
			
			if(i == 13) {
				e1 = document.getElementById("selectTagParada");
				return e1.options[e1.selectedIndex].text;
			}
			
			if(i == 14) {
				e1 = document.getElementById("selectPitStop");
				return e1.options[e1.selectedIndex].text;
			}
			
            return $.trim($this.find('input').val());
          }
        });

        this.datatable.row($row.get(0)).data(values);

        $actions = $row.find('td.actions');
        if ($actions.get(0)) {
          this.rowSetActionsDefault($row);
        }
		
        this.datatable.draw();
	}
      },

      rowRemove: function($row) {
        if ($row.hasClass('adding')) {
          this.$addButton.removeAttr('disabled');
        }
		var id = $row.get(0).cells[0].innerHTML;
		var link = "/ActionCadastroRotasTags?action=delete&id=" + id;
		if(action == "delete") {
			if(enviarDados(link)) {
				this.datatable.row($row.get(0)).remove().draw();
			}
		} else {
			this.datatable.row($row.get(0)).remove().draw();
		}
      },

      rowSetActionsEditing: function($row) {
        $row.find('.on-editing').removeClass('hidden');
        $row.find('.on-default').addClass('hidden');
      },

      rowSetActionsDefault: function($row) {
        $row.find('.on-editing').addClass('hidden');
        $row.find('.on-default').removeClass('hidden');
      }

    };

    $(function() {
      EditableTable.initialize();
    });

  }).apply(this, [jQuery]);


})(document, window, jQuery);
