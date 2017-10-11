package AGVS.Controle.Goodyear;

import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Semaphore;

import AGVS.Data.AGV;
import AGVS.Data.ConfigProcess;
import AGVS.Data.Equipamentos;
import AGVS.Data.Semaforo;
import AGVS.Data.Supermercado;
import AGVS.Util.Log;
import AGVS.Util.Util;

public class XMLControleGoodyear extends Thread {

	private List<Pedido> pedidos;
	private Semaphore sp;
	private static long id = 1;

	public List<Pedido> getPedidos() {
		return pedidos;
	}

	public XMLControleGoodyear() {
		pedidos = new ArrayList<Pedido>();
		sp = new Semaphore(1);
		this.start();
	}

	private boolean agvEmProcesso(int id) {
		for (Pedido pedido : pedidos) {
			if (pedido.getAgv() != null && pedido.getAgv().getId() == id) {
				return true;
			}
		}
		return false;
	}

	public void pedidoFinalizadoID(int id) {
		try {
			sp.acquire();
			if (pedidos != null) {
				for (int i = 0; i < pedidos.size(); i++) {
					Pedido pedido = pedidos.get(i);
					if (pedido.getAgv() != null && pedido.getId() == id) {

						// Atualiza Itens
						if (pedido.getSupermercadoA() != null) {
							ConfigProcess.bd().updateSupermercados(pedido.getSupermercadoA().getNome(),
									pedido.getSupermercadoA().getProduto(), pedido.getSupermercadoA().getId(),
									System.currentTimeMillis(), pedido.getSupermercadoA().getNome());
						}

						if (pedido.getSupermercadoB() != null) {
							ConfigProcess.bd().updateSupermercados(pedido.getSupermercadoB().getNome(),
									pedido.getSupermercadoB().getProduto(), pedido.getSupermercadoB().getId(),
									System.currentTimeMillis(), pedido.getSupermercadoB().getNome());
						}
						pedidos.remove(i);
						i--;
					}
				}
			}
		} catch (Exception e) {
			new Log(e);
		}
		sp.release();
	}

	public void removePedido(int id) {
		try {
			sp.acquire();
			if (pedidos != null) {
				for (int i = 0; i < pedidos.size(); i++) {
					Pedido pedido = pedidos.get(i);
					if (pedido.getId() == id) {

						pedidos.remove(i);
						i--;
					}
				}
			}
		} catch (Exception e) {
			new Log(e);
		}
		sp.release();
	}

	public void pedidoFinalizado(int idAGV) {
		try {
			sp.acquire();
			if (pedidos != null) {
				for (int i = 0; i < pedidos.size(); i++) {
					Pedido pedido = pedidos.get(i);
					if (pedido.getAgv() != null && pedido.getAgv().getId() == idAGV) {

						// Atualiza Itens
						if (pedido.getSupermercadoA() != null) {
							ConfigProcess.bd().updateSupermercados(pedido.getSupermercadoA().getNome(),
									pedido.getSupermercadoA().getProduto(), pedido.getSupermercadoA().getId(),
									System.currentTimeMillis(), pedido.getSupermercadoA().getNome());
						}

						if (pedido.getSupermercadoB() != null) {
							ConfigProcess.bd().updateSupermercados(pedido.getSupermercadoB().getNome(),
									pedido.getSupermercadoB().getProduto(), pedido.getSupermercadoB().getId(),
									System.currentTimeMillis(), pedido.getSupermercadoB().getNome());
						}
						pedidos.remove(i);
						i--;
					}
				}
			}
		} catch (Exception e) {
			new Log(e);
		}
		sp.release();
	}

	public void run() {
		while (true) {
			try {
				sp.acquire();

				File f = new File("C:/XMLS");

				List<Supermercado> supermercados = ConfigProcess.bd().selectSupermercados();
				List<Equipamentos> equipamentos = ConfigProcess.bd().selectEquipamentos();
				List<AGV> agvs = ConfigProcess.bd().selecAGVS();

				for (String string : f.list()) {

					if (string.contains(".xml")) {
						System.out.println(string);
						File read = new File("C:/XMLS/" + string);
						try {
							Pedido pedido = new Pedido(id++, null,
									Util.getXml("codigo", read.getAbsolutePath()).toUpperCase(),
									Util.getXml("endereco", read.getAbsolutePath()).toUpperCase(),
									Util.getXml("tipo", read.getAbsolutePath()).toUpperCase(), null, null,
									Util.getXml("retorno", read.getAbsolutePath()).toUpperCase());

							if (pedido.getTipo().toUpperCase().equals("MERCADO")) {
								boolean ok = false;
								Supermercado sp = null;
								for (int i = 0; supermercados != null && i < supermercados.size(); i++) {
									Supermercado mercado = supermercados.get(i);
									if (mercado.getNome().toUpperCase().equals(pedido.getIdEquipamento())) {
										if (!mercado.getProduto().toUpperCase().equals("EM USO")) {
											ok = true;
											sp = mercado;
										}
										i = supermercados.size();
									}
								}
								File resp = new File("C:/XMLSRESP/" + System.currentTimeMillis()
										+ pedido.getIdEquipamento() + ".xml");
								resp.createNewFile();
								FileWriter fw = new FileWriter(resp);
								fw.write("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n");
								fw.write("<pedidos>\n");
								fw.write("  <produto>\n");
								fw.write("    <codigo>" + pedido.getIdProduto() + "</codigo>\n");
								fw.write("    <endereco>" + pedido.getIdEquipamento() + "</endereco>\n");
								fw.write("    <tipo>RESPOSTA</tipo>\n");
								if (ok) {
									fw.write("    <retorno>OK</retorno>\n");

									ConfigProcess.bd().updateSupermercados(sp.getNome(), pedido.getIdProduto(),
											sp.getId(), System.currentTimeMillis(), sp.getNome());

								} else {
									fw.write("    <retorno>ERRO</retorno>\n");
								}
								fw.write("  </produto>\n");
								fw.write("</pedidos>\n");
								fw.close();

							} else {
								pedidos.add(pedido);
							}
							read.delete();
							read.deleteOnExit();
						} catch (Exception e) {
							new Log(e);
						}
					}
				}

				if (agvs != null) {

					for (int i = 0; i < agvs.size(); i++) {
						for (int j = i + 1; j < agvs.size(); j++) {
							if (agvs.get(i).getTagAtualTime() > agvs.get(j).getTagAtualTime()) {
								AGV aux = agvs.get(i);
								agvs.set(i, agvs.get(j));
								agvs.set(j, aux);
							}
						}
					}
				}

				// ordena o supermercado
				if (supermercados != null) {
					for (int i = 0; i < supermercados.size(); i++) {
						for (int j = i + 1; j < supermercados.size(); j++) {
							if (supermercados.get(i).getData() < supermercados.get(j).getData()) {
								Supermercado aux = supermercados.get(i);
								supermercados.set(i, supermercados.get(j));
								supermercados.set(j, aux);
							}
						}
					}

				}

				for (int i = 0; pedidos != null && i < pedidos.size(); i++) {

					Pedido pedido = pedidos.get(i);
					// Procura um agv para sair com o pedido

					if (pedido.getRetorno().equals("")) {
						pedido.setRetorno("VAZIO");
					}

					if (agvs != null) {
						for (AGV agv : agvs) {
							if ((pedido.getAgv() == null && agv.getStatus().equals(AGV.statusHome)
									&& !agvEmProcesso(agv.getId()))
									|| (agvEmProcesso(agv.getId()) && agv.getStatus().equals(AGV.statusHome)
											&& pedido.getAgv() != null && pedido.getAgv().getId() == agv.getId())) {

								int[][] rotas = pedido.getRotas();
								if (rotas != null) {
									AGV.sendRota(rotas, agv.getMac16(), agv.getMac64());
								} else {

									rotas = new int[10][2];
									rotas[0][0] = 0xFF;
									rotas[0][1] = 0xFF;
									rotas[1][0] = 0xFF;
									rotas[1][1] = 0xFF;
									rotas[2][0] = 0xFF;
									rotas[2][1] = 0xFF;
									rotas[3][0] = 0xFF;
									rotas[3][1] = 0xFF;
									rotas[4][0] = 0xFF;
									rotas[4][1] = 0xFF;
									rotas[5][0] = 0xFF;
									rotas[5][1] = 0xFF;
									rotas[6][0] = 0xFF;
									rotas[6][1] = 0xFF;
									rotas[7][0] = 0xFF;
									rotas[7][1] = 0xFF;
									rotas[8][0] = 0xFF;
									rotas[8][1] = 0xFF;
									rotas[9][0] = 0xFF;
									rotas[9][1] = 0xFF;
									boolean ok = false;
									// Monta Logica TBM
									if (pedido.getTipo().equals("TBM")) {
										String Mercado = "";
										String TBM = "";
										String PontoCarga = "";

										ok = false;
										// 1 - Vai no Mercado Pegar Produto
										// 3 - Devolve Carrinho ao Mercado
										if (supermercados != null) {
											for (Supermercado supermercado : supermercados) {
												if (supermercado.getProduto().toUpperCase()
														.equals(pedido.getIdProduto())) {
													rotas[0][0] = 1;
													rotas[0][1] = supermercado.getId();
													rotas[2][0] = 1;
													rotas[2][1] = supermercado.getId() + 1;
													ok = true;
													Mercado = supermercado.getNome();
													// Salva Novos status
													// mercado
													supermercado.setProduto(pedido.getRetorno());
													pedido.setSupermercadoA(supermercado);

												}
											}
										}
										// 2 - Vai para TBM
										if (ok) {
											ok = false;
											if (equipamentos != null) {
												for (Equipamentos equipamento : equipamentos) {
													if (equipamento.getNome().toUpperCase()
															.equals(pedido.getIdEquipamento())) {
														ok = true;
														rotas[1][0] = equipamento.getRota();
														rotas[1][1] = 1;

														TBM = equipamento.getNome();
													}
												}
											}

										}
										// 4 - Retorna ao Ponto de Carga
										if (ok) {
											rotas[3][0] = 22;
											rotas[3][1] = agv.getId();

											// Envia Rota e libera AGV
											pedido.setAgv(agv);
											pedido.setRotas(rotas);
											AGV.sendRota(rotas, agv.getMac16(), agv.getMac64());

											PontoCarga = "Home " + agv.getId();

											pedido.setLog(Mercado + "|" + TBM + "|" + Mercado + "|" + PontoCarga);

											if (pedido.getSupermercadoA() != null) {
												ConfigProcess.bd().updateSupermercados(
														pedido.getSupermercadoA().getNome(), "EM USO",
														pedido.getSupermercadoA().getId(), System.currentTimeMillis(),
														pedido.getSupermercadoA().getNome());
											}

											if (pedido.getSupermercadoB() != null) {
												ConfigProcess.bd().updateSupermercados(
														pedido.getSupermercadoB().getNome(), "EM USO",
														pedido.getSupermercadoB().getId(), System.currentTimeMillis(),
														pedido.getSupermercadoB().getNome());
											}

										}

									}

									// Monta Logica Nakata
									if (pedido.getTipo().equals("NAKATA")) {
										String MercadoA = "";
										String MercadoB = "";
										String Nakate = "";
										String PontoCarga = "";

										ok = true;
										// 1 - Vai Nakata Pegar produto
										// 4 - Leva para Nakata
										if (ok) {
											ok = false;
											if (equipamentos != null) {
												for (Equipamentos equipamento : equipamentos) {
													if (equipamento.getNome().toUpperCase()
															.equals(pedido.getIdEquipamento())) {
														ok = true;
														rotas[0][0] = equipamento.getRota();
														rotas[0][1] = 1;
														rotas[3][0] = equipamento.getRota();
														rotas[3][1] = 2;
														Nakate = equipamento.getNome();
													}
												}
											}

										}
										// 2 - Leva produto para
										// Supermercado
										if (ok) {
											ok = false;
											if (supermercados != null) {
												for (Supermercado supermercado : supermercados) {
													if (supermercado.getProduto().toUpperCase().equals("VAGO")) {
														rotas[1][0] = 1;
														rotas[1][1] = supermercado.getId() + 1;
														ok = true;
														supermercado.setProduto(pedido.getIdProduto().toUpperCase());
														pedido.setSupermercadoA(supermercado);
														MercadoA = supermercado.getNome();
													}

												}
											}
										}
										// 3 - Pega Vazio Supermecado
										if (ok) {
											ok = false;
											if (supermercados != null) {
												for (Supermercado supermercado : supermercados) {
													if (supermercado.getProduto().toUpperCase().equals("VAZIO")) {
														rotas[2][0] = 1;
														rotas[2][1] = supermercado.getId();
														ok = true;
														supermercado.setProduto("VAGO");
														pedido.setSupermercadoB(supermercado);
														MercadoB = supermercado.getNome();
													}
												}
											}
										}

										// 5 - Retorna Ponto de Carga
										// Retorna ao Ponto de Carga
										if (ok) {
											rotas[4][0] = 22;
											rotas[4][1] = agv.getId();
											// Envia Rota e libera AGV
											pedido.setAgv(agv);
											AGV.sendRota(rotas, agv.getMac16(), agv.getMac64());
											PontoCarga = "Home " + agv.getId();
											pedido.setLog(Nakate + "|" + MercadoA + "|" + MercadoB + "|" + PontoCarga);
											pedido.setRotas(rotas);
											if (pedido.getSupermercadoA() != null) {
												ConfigProcess.bd().updateSupermercados(
														pedido.getSupermercadoA().getNome(), "EM USO",
														pedido.getSupermercadoA().getId(), System.currentTimeMillis(),
														pedido.getSupermercadoA().getNome());
											}

											if (pedido.getSupermercadoB() != null) {
												ConfigProcess.bd().updateSupermercados(
														pedido.getSupermercadoB().getNome(), "EM USO",
														pedido.getSupermercadoB().getId(), System.currentTimeMillis(),
														pedido.getSupermercadoB().getNome());
											}
										}
									}

								}
							}
						}
					}

					// Gerencia a Rota para o agv executar
				}

				sp.release();
				Thread.sleep(3000);
			} catch (Exception e) {
				sp.release();
				new Log(e);
			}
		}
	}
}
