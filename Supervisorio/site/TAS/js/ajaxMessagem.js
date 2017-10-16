


var MSGAGUARDE = -1

function MSGUsuarios(msg, link){
  bootbox.dialog({
	title: "Paginas permitidas para Usuario",
	message: msg,
	buttons: {
          success: {
            label: "Salvar",
            className: "btn-success",
            callback: function() {
				var resp = '/ActionUsuario?action=editPermissao&id=' + link;
				for (var i = 0; i < document.getElementsByName('tokens').length; i++) {
					resp += '&' + document.getElementsByName('tokens')[i].id + '=' + document.getElementsByName('tokens')[i].checked;
					resp = removerAcentos(resp);
				}
				enviarDados(resp);
				
            }
          }
        }
  });
}
  
function MSGCruzamentoAGV(msg, link){
bootbox.dialog({
title: "Adicionar AGV em Cruzamento",
message: msg,
buttons: {
	  success: {
		label: "Salvar",
		className: "btn-success",
		callback: function() {
			var resp = '/ActionCadastroCruzamentos?action=add&id=' + link;
			for (var i = 0; i < document.getElementsByName('tokens').length; i++) {
				resp += '&' + document.getElementsByName('tokens')[i].id + '=' + document.getElementsByName('tokens')[i].checked;
				resp = removerAcentos(resp);
			}
			enviarDados(resp);
			
		}
	  },
	  danger: {
		label: "Cancelar",
		className: "btn-danger",
		callback: function() {
		}
	  },
	}
});
}

function MSGCruzamentoAGVDel(msg, link){
bootbox.dialog({
title: "Adcionar AGV em Cruzamento",
message: msg,
buttons: {
	  success: {
		label: "Salvar",
		className: "btn-success",
		callback: function() {
			var resp = '/ActionCadastroCruzamentos?action=del&id=' + link;
			for (var i = 0; i < document.getElementsByName('tokens').length; i++) {
				resp += '&' + document.getElementsByName('tokens')[i].id + '=' + document.getElementsByName('tokens')[i].checked;
				resp = removerAcentos(resp);
			}
			enviarDados(resp);
			
		}
	  },
	  danger: {
		label: "Cancelar",
		className: "btn-danger",
		callback: function() {
		}
	  },
	}
});
}
function MSGRotas(msg, link){
  bootbox.dialog({
	title: "Configurando Rotas",
	message: msg,
	buttons: {
          success: {
            label: "Enviar",
            className: "btn-success",
            callback: function() {
				var resp = '/ActionCadastroAGVS?action=rota&id=' + link;
				for (var i = 0; i < 10; i++) {
					resp += '&r' + i + '=' + document.getElementById("r" + i).value;
					resp += '&c' + i + '=' + document.getElementById("c" + i).value;
					
				}
				enviarDados(resp);
            }
          },
		  danger: {
			label: "Cancelar",
			className: "btn-danger",
			callback: function() {
            }
		  },
        }
  });
}

function requestPopupRotas(link)
{
	try
	{
		$.ajax({
			method: "GET",
			url: link,
			async: true,
			contentType: "text/html",
			success: function (e){
				var msg = e;
				var teste = "";
				if(msg.length >= 2) {
					teste = msg.substring(0, 2);
				}
				if(teste == "OK") 
				{
					msg = msg.substring(2, msg.length);
					var array = msg.split("#");
					MSGRotas(array[0], array[1]);
					return true;
				}
				else 
				{
					menssagemErro('Falha ao realizar pedido, Verifique se possui permissão para realizar ação.');
					return false;
				}
			},
			error: function (error){
//				console.log("retorno erro");
			}
		});
		//////////////////////////////////////////////////

//		var ajax = AjaxF();	
//		ajax.open("GET", link, false);
//		ajax.setRequestHeader("Content-Type", "text/html");
//		ajax.send();
//		while (ajax.readyState != 4) {
//			
//		}
//		var msg = ajax.responseText;
		
		
	}
	catch(e) 
	{
		menssagemErro('Falha na comunica��o');
		return false;
	}
}

//ALTERAÇÃO DANIEL - SEARCH BY DATE

//function searchByDate(link)
//{
//	if(!$('#frmSeachByDate')[0].checkValidity()) {
//		return false;
//	}
//	try{
//		var dateStart = $("input[name=start]").val();
//		var dateEnd = $("input[name=end]").val();
//		$.ajax({
//			method: "GET",
//			url: link + "?action=search&datestart="+dateStart+"&dateend="+dateEnd,
//			async: true,
//			contentType: "text/html",
//			success: function (e){
//				var msg = e;
//				var teste = "";
//				if(msg.length >= 2) {
//					teste = msg.substring(0, 2);
//				}
//				if(teste == "OK"){
//					msg = msg.substring(2, msg.length);
//					$("#iframereportevents").contents().find('#exampleTableSearch > tbody').html(msg);
//				}
//				else 
//				{
//					menssagemErro('Falha ao realizar pedido, Verifique se possui permissão para realizar ação.');
//				}
//			},
//			error: function (error){
//				console.log("retorno erro");
//			}
//		});	
//		
//	}
//	catch(e) 
//	{
//		menssagemErro('Falha na comunicação');
//		return false;
//	}
//}

function MSGAGVInicioTurno(msg, link){
bootbox.dialog({
title: "Adicionar AGV Parada Inicio de Turno",
message: msg,
buttons: {
	  success: {
		label: "Salvar",
		className: "btn-success",
		callback: function() {
			var resp = '/ActionCadastroTags?action=add&id=' + link;
			for (var i = 0; i < document.getElementsByName('tokens').length; i++) {
				resp += '&' + document.getElementsByName('tokens')[i].id + '=' + document.getElementsByName('tokens')[i].checked;
				resp = removerAcentos(resp);
			}
			enviarDados(resp);
			
		}
	  },
	  danger: {
		label: "Cancelar",
		className: "btn-danger",
		callback: function() {
		}
	  },
	}
});
}



function BtnEmergencia(id){
	var resp = '/ActionCadastroAGVS?action=emergencia&id=' + id;
	enviarDados(resp);
}

function BtnPlay(id){
  var resp = '/ActionCadastroAGVS?action=play&id=' + id;
  enviarDados(resp);
}

function BtnPlayRe(id){
	var resp = '/ActionCadastroAGVS?action=playre&id=' + id;
	enviarDados(resp);
}

function BtnParar(id){
	var resp = '/ActionCadastroAGVS?action=parar&id=' + id;
	enviarDados(resp);
}

function BtnPitStop(id){
	var resp = '/ActionCadastroAGVS?action=pitstop&id=' + id;
	enviarDados(resp);
}

function BtnVerde(id){
	var resp = '/ActionCadastroTags?action=semafaro&id=' + id + '&cmd=vd';
	enviarDados(resp);
}

function BtnVermelho(id){
	var resp = '/ActionCadastroTags?action=semafaro&id=' + id + '&cmd=vm';
	enviarDados(resp);
}

function BtnAmarelo(id){
	var resp = '/ActionCadastroTags?action=semafaro&id=' + id + '&cmd=am';
	enviarDados(resp);
}

function BtnDesligar(id){
	var resp = '/ActionCadastroTags?action=semafaro&id=' + id + '&cmd=des';
	enviarDados(resp);
}

function BtnVerdeA(id){
	var resp = '/ActionCadastroTags?action=semafaro&id=' + id + '&cmd=vda';
	enviarDados(resp);
}

function BtnAmareloA(id){
	var resp = '/ActionCadastroTags?action=semafaro&id=' + id + '&cmd=ama';
	enviarDados(resp);
}

function BtnVermelhoA(id){
	var resp = '/ActionCadastroTags?action=semafaro&id=' + id + '&cmd=vma';
	enviarDados(resp);
}



function MSGFalha(msg){
  bootbox.dialog({
	title: "Falha ocorrida",
	message: msg,
	buttons: {
          success: {
            label: "OK",
            className: "btn-danger",
            callback: function() {
				
            }
          }
        }
  });
}

function SenhaUsuarios(){
  bootbox.dialog({
	title: "Alterar senha do Usuario",
	message: '<h4 class="example-title">Senha</h4>' +
			 '<input type="password" class="form-control focus" id="password" name="password" value="">',
	buttons: {
          success: {
            label: "Alterar",
            className: "btn-success",
            callback: function() {
				var resp = '/ActionUsuario?action=alterarSenha&password=' + document.getElementById('password').value;
				enviarDados(resp);
            }
          },
		  danger: {
			label: "Cancelar",
			className: "btn-danger",
			callback: function() {
            }
		  },
		  
        }
  });
}

function AjaxF()
{
	var ajax;
	
	try
	{
		ajax = new XMLHttpRequest();
	} 
	catch(e) 
	{
		try
		{
			ajax = new ActiveXObject("Msxml2.XMLHTTP");
		}
		catch(e) 
		{
			try 
			{
				ajax = new ActiveXObject("Microsoft.XMLHTTP");
			}
			catch(e) 
			{
				alert("Seu browser n�o da suporte � AJAX!");
				return false;
			}
		}
	}
	return ajax;
}

function refreshElement(link, id)
{
	try
	{
		$.ajax({
			method: "GET",
			url: link,
			async: false,
			contentType: "text/html",
			success: function (e){
				var msg = e;
				var corPF = "";
				var conteudo = "";
				if(msg.length >= 8) {
					corPF = msg.substring(0, 8);
					conteudo = msg.substring(8);
				}
				document.getElementById(id).innerHTML = conteudo;
				$('body').css('background-color', corPF);
				
			},
			error: function (error){
//				console.log("retorno erro");
			}
		});		
	}
	catch(e) 
	{
	}
}

function requestPopupFalhas(link)
{
	try
	{
		$.ajax({
			method: "GET",
			url: link,
			async: true,
			contentType: "text/html",
			success: function (e){
				var msg = e;
				var teste = "";
				if(msg.length >= 2) {
					teste = msg.substring(0, 2);
				}
				if(teste == "OK") {
					msg = msg.substring(2, msg.length);
					MSGFalha(msg);
				}
			},
			error: function (error){
//				console.log("retorno erro");
			}
		});		
		
			
	}
	catch(e) 
	{
	}
}

function requestPopupUsers(link)
{
	try
	{
		$.ajax({
			method: "GET",
			url: link,
			async: true,
			contentType: "text/html",
			success: function (e){
				var msg = e;
				var teste = "";
				if(msg.length >= 2) {
					teste = msg.substring(0, 2);
				}
				if(teste == "OK") 
				{
					msg = msg.substring(2, msg.length);
					var array = msg.split("#");
					MSGUsuarios(array[0], array[1]);
					return true;
				}
				else 
				{
					menssagemErro('Falha ao realizar pedido, Verifique se possui permissão para realizar ação.');
					return false;
				}
			},
			error: function (error){
				console.log("retorno erro: " + error);
			}
		});			
	}
	catch(e) 
	{
		menssagemErro('Falha na comunica��o');
		return false;
	}
}

function requestPopupCruzamentosAGVS(link)
{
	try
	{
		$.ajax({
			method: "GET",
			url: link,
			async: true,
			contentType: "text/html",
			success: function (e){
				var msg = e;
				var teste = "";
				if(msg.length >= 2) {
					teste = msg.substring(0, 2);
				}
				if(teste == "OK") 
				{
					msg = msg.substring(2, msg.length);
					var array = msg.split("#");
					MSGCruzamentoAGV(array[0], array[1]);
					return true;
				}
				else 
				{
					menssagemErro('Falha ao realizar pedido, Verifique se possui permiss�o para realizar a��o.');
					return false;
				}
			},
			error: function (error){
//				console.log("retorno erro");
			}
		});	
	}
	catch(e) 
	{
		menssagemErro('Falha na comunica��o');
		return false;
	}
}

function requestPopupCruzamentosAGVSDel(link)
{
	try
	{
		
		$.ajax({
			method: "GET",
			url: link,
			async: true,
			contentType: "text/html",
			success: function (e){
				var msg = e;
				var teste = "";
				if(msg.length >= 2) {
					teste = msg.substring(0, 2);
				}
				if(teste == "OK") 
				{
					msg = msg.substring(2, msg.length);
					var array = msg.split("#");
					MSGCruzamentoAGVDel(array[0], array[1]);
					return true;
				}
				else 
				{
					menssagemErro('Falha ao realizar pedido, Verifique se possui permiss�o para realizar a��o.');
					return false;
				}
			},
			error: function (error){
//				console.log("retorno erro");
			}
		});	
	}
	catch(e) 
	{
		menssagemErro('Falha na comunica��o');
		return false;
	}
}


function requestPopupAddAGVInicioTurno(link)
{
	try
	{
		$.ajax({
			method: "GET",
			url: link,
			async: true,
			contentType: "text/html",
			success: function (e){
				var msg = e;
				var teste = "";
				if(msg.length >= 2) {
					teste = msg.substring(0, 2);
				}
				if(teste == "OK") 
				{
					msg = msg.substring(2, msg.length);
					var array = msg.split("#");
					MSGAGVInicioTurno(array[0], array[1]);
					return true;
				}
				else 
				{
					menssagemErro('Falha ao realizar pedido, Verifique se possui permiss�o para realizar a��o.');
					return false;
				}
			},
			error: function (error){
//				console.log("retorno erro");
			}
		});
	}
	catch(e) 
	{
		menssagemErro('Falha na comunica��o');
		return false;
	}
}

// Fun��o que faz as requisi��o Ajax ao arquivo PHP
function enviarDadosNoMSG(link)
{
	try
	{
		$.ajax({
			method: "GET",
			url: link,
			async: false,
			contentType: "text/html",
			success: function (e){
				var msg = e;
				var teste = "";
				if(msg.length >= 2) {
					teste = msg.substring(0, 2);
				}
				if(teste == "OK") 
				{
					return true;
				}
				else if (teste == "US")
				{
					return true;
				}
				else 
				{
					menssagemErro(msg);
					return false;
				}
			},
			error: function (error){
				console.log("retorno erro");
			}
		});	
		return true;
	}
	catch(e) 
	{
		menssagemErro('Falha na comunicação');
		return false;
	}
}


// Função que faz as requisição Ajax ao arquivo PHP
function enviarDados(link)
{
	var ret = false;
	try
	{
		$.ajax({
			method: "GET",
			url: link,
			async: false,
			contentType: "text/html",
			success: function (e){
				var msg = e;
				var teste = "";
				if(msg.length >= 2) {
					teste = msg.substring(0, 2);
				}
				if(teste == "OK") 
				{
					menssagemOK('Comando realizado com sucesso.');
					ret = true;
				}
				else if (teste == "US")
				{
					menssagemOKUsr('Senha alterada com sucesso.');
					ret = true;
				}
				else if (teste == "ID")
				{
					var lengthId = msg.substring(2,3);
					ret = msg.substring(3,(3+lengthId));
					menssagemOK('Registro inserido com sucesso.');
				}
				else 
				{
					menssagemErro(msg);
					ret = false;
				}
			},
			error: function (error){
				ret = false;
			}
		});	
		return ret;
	}
	catch(e) 
	{
		menssagemErro('Falha na comunição: ' + e);
		return false;
	}
}

function menssagemOK(msg)
{
	 
	toastr.info(msg);
	  
	  
}

function menssagemOKUsr(msg)
{
	 
	bootbox.dialog({
		closeButton: false,
        message: msg,
        title: "Menssagem do sistema",
        buttons: {
          success: {
            label: "OK",
            className: "btn-success",
            callback: function() {
				location.href='/SupervisorioAGVS/Login';
            }
          }
        }
      });
	  
	  
}

function menssagemErro(msg)
{
	 
	toastr.info(msg);
	  
}



function removerAcentos( newStringComAcento ) {
  var string = newStringComAcento;
	var mapaAcentosHex = {
		a : /[\xE0-\xE6]/g,
		A : /[\xC0-\xC6]/g,
		e : /[\xE8-\xEB]/g,
		E : /[\xC8-\xCB]/g,
		i : /[\xEC-\xEF]/g,
		I : /[\xCC-\xCF]/g,
		o : /[\xF2-\xF6]/g,
		O : /[\xD2-\xD6]/g,
		u : /[\xF9-\xFC]/g,
		U : /[\xD9-\xDC]/g,
		c : /\xE7/g,
		C : /\xC7/g,
		n : /\xF1/g,
		N : /\xD1/g,
	};
	

	for ( var letra in mapaAcentosHex ) {
		var expressaoRegular = mapaAcentosHex[letra];
		string = string.replace( expressaoRegular, letra );
	}

	return string;
}

