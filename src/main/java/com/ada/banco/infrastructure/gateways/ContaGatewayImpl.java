package com.ada.banco.infrastructure.gateways;

import com.ada.banco.application.gateways.ContaGateway;
import com.ada.banco.application.gateways.UsuarioGateway;
import com.ada.banco.domain.model.Conta;
import com.ada.banco.domain.model.Usuario;
import com.ada.banco.infrastructure.persistence.entities.ContaEntity;
import com.ada.banco.infrastructure.persistence.repositories.ContaRepository;

public class ContaGatewayImpl implements ContaGateway {

    private final ContaMapper contaMapper;
    private final ContaRepository contaRepository;

    private final UsuarioGatewayImpl usuarioGateway;

    private final UsuarioMapper usuarioMapper;


    public ContaGatewayImpl(
            ContaMapper contaMapper,
            ContaRepository contaRepository,
            UsuarioGatewayImpl usuarioGateway,
            UsuarioMapper usuarioMapper
    ){
        this.contaMapper = contaMapper;
        this.contaRepository = contaRepository;
        this.usuarioGateway = usuarioGateway;
        this.usuarioMapper = usuarioMapper;
    }



    @Override
    public Conta criarConta(Conta conta) throws Exception {
        if(conta.titular() == null){
            throw new Exception("Não é possível salvar conta sem titular");
        }

        if(conta.numeroConta() == null){
            throw new Exception("Não é possível criar conta sem um número");
        }

        if(conta.id() != null){
            throw new Exception("Não é possível criar conta com id registrado, apenas atualizar");
        }

        if(conta.tipoConta() == null ){
            throw new Exception("Tipo da conta inválido ou em branco.");
        }

        if(contaRepository.findByNumeroConta(conta.numeroConta()).isPresent()){
            throw new Exception("Não é possível criar duas contas com mesmo número");
        }

        if(usuarioGateway.buscarUsuarioPorId(conta.titular()) == null){
            throw new Exception("Titular dessa conta é inválido");
        }

        if(conta.titular() == null){
            throw  new Exception("Não é possível criar conta sem titular");
        }

        if(contaRepository.findByTitular(conta.titular()).isPresent()){
            throw  new Exception("Titular já possui uma conta registrada.");
        }

        ContaEntity contaEntity = new ContaEntity();
        contaMapper.toEntity(conta, contaEntity);
        contaRepository.save(contaEntity);

        return contaMapper.toDomain(contaEntity);
    }

    @Override
    public Conta atualizarConta(Conta conta) throws Exception {
        if(conta.titular() == null){
            throw new Exception("Não é possível salvar conta sem titular");
        }

        if(conta.numeroConta() == null){
            throw new Exception("Não é possível atualizar conta sem um número");
        }

        if(conta.tipoConta() == null || (!conta.tipoConta().equalsIgnoreCase("corrente") && !conta.tipoConta().equalsIgnoreCase("poupança"))){
            throw new Exception("Tipo da conta inválido ou em branco.");
        }

        if(conta.titular() == null){
            throw  new Exception("Não é possível atualizar conta sem titular");
        }

        ContaEntity contaEntity = contaRepository.findById(conta.id()).get();
        contaMapper.toEntity(conta, contaEntity);
        contaRepository.save(contaEntity);
        return conta;
    }

    @Override
    public Conta buscarContaPorCpf(String cpf) throws Exception {
        if(cpf == null){
            throw new Exception("CPF nulo.");
        }

        Usuario usuario = usuarioGateway.buscarUsuarioPorCpf(cpf);

        if(usuario == null){
            throw new Exception("Não existe usuário com esse cpf.");
        }
        return contaMapper.toDomain(contaRepository.findByTitular(usuario.id()).get());
    }

    @Override
    public Conta buscarContaPorId(Long id) throws Exception {
        if(id == null){
            throw new Exception("Id inválido");
        }

        if(contaRepository.findById(id).isEmpty()){
            throw new Exception("Não existe conta com esse id.");
        }


        return contaMapper.toDomain(contaRepository.findById(id).get());
    }

    @Override
    public Conta buscarContaPorNumero(String numeroConta) throws Exception {
        if(numeroConta == null){
            throw new Exception("Número da conta inválido");
        }

        if(contaRepository.findByNumeroConta(numeroConta).isEmpty()){
            throw new Exception("Não existe conta com esse número.");
        }
        return contaMapper.toDomain(contaRepository.findByNumeroConta(numeroConta).get());
    }
}
