function salvarBD() {
	var link = '/ActionBancoDados?action=salvar&end=' + document.getElementById('end').value + '&user=' + document.getElementById('user').value + '&password=' + document.getElementById('password').value
	enviarDados(link);
}

function criarBD() {
	var link = '/ActionBancoDados?action=criar&end=' + document.getElementById('end').value + '&user=' + document.getElementById('user').value + '&password=' + document.getElementById('password').value
	enviarDados(link);
}