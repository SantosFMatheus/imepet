package com.imepac.imepet.service;

import com.imepac.imepet.model.DadosSocioeconomicosModel;
import com.imepac.imepet.model.TutorModel;
import com.imepac.imepet.repositories.TutorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;


@Service
public class TutorService {

    private final TutorRepository tutorRepository;

    @Autowired
    public TutorService(TutorRepository tutorRepository) {
        this.tutorRepository = tutorRepository;
    }

    public TutorModel salvar(TutorModel tutor) {
        if (tutor.getStatus() == null) {
            tutor.setStatus("Em análise");
        }
        return tutorRepository.save(tutor);
    }

    public List<TutorModel> listarTodos() {
        return tutorRepository.findAll();
    }

    public Optional<TutorModel> buscarPorId(Long id) {
        return tutorRepository.findById(id);
    }

    public void deletarPorId(Long id) {
        tutorRepository.deleteById(id);
    }

    public List<Map<String, Object>> listarCamposResumidos() {
        return tutorRepository.findAll().stream().map(tutor -> {
            Map<String, Object> dados = new HashMap<>();
            dados.put("id", tutor.getId());
            dados.put("nome", tutor.getNome());
            dados.put("dataNascimento", tutor.getDataNascimento());
            dados.put("status", tutor.getStatus());
            dados.put("cpf", tutor.getCpf());
            dados.put("rg", tutor.getRg());
            dados.put("celular", tutor.getCelular());
            return dados;
        }).toList();
    }

    public List<Map<String, Object>> listarCamposResumidosPorNome(String nome) {
        return tutorRepository.findByNomeContainingIgnoreCase(nome).stream().map((TutorModel tutor) -> {
            Map<String, Object> dados = new HashMap<>();
            dados.put("id", tutor.getId());
            dados.put("nome", tutor.getNome());
            dados.put("dataNascimento", tutor.getDataNascimento());
            dados.put("status", tutor.getStatus());
            dados.put("cpf", tutor.getCpf());
            dados.put("rg", tutor.getRg());
            dados.put("celular", tutor.getCelular());
            return dados;
        }).toList();
    }

    public List<TutorModel> buscarPorNome(String nome) {
        return tutorRepository.findByNomeContainingIgnoreCase(nome);
    }

    public boolean existePorId(Long id) {
        return tutorRepository.existsById(id);
    }

    public void atualizarTutorExistente(TutorModel tutorAtualizado) {
        TutorModel tutorExistente = tutorRepository.findById(tutorAtualizado.getId())
                .orElseThrow(() -> new RuntimeException("Tutor não encontrado"));

        // Atualização dos campos de TutorModel
        tutorExistente.setNome(tutorAtualizado.getNome());
        tutorExistente.setCelular(tutorAtualizado.getCelular());
        tutorExistente.setCpf(tutorAtualizado.getCpf());
        tutorExistente.setRg(tutorAtualizado.getRg());
        tutorExistente.setDataNascimento(tutorAtualizado.getDataNascimento());
        tutorExistente.setNaturalidade(tutorAtualizado.getNaturalidade());
        tutorExistente.setEstadoCivil(tutorAtualizado.getEstadoCivil());
        tutorExistente.setNomeMarido(tutorAtualizado.getNomeMarido());
        tutorExistente.setTemFilhos(tutorAtualizado.getTemFilhos());
        tutorExistente.setQuantidadeFilhos(tutorAtualizado.getQuantidadeFilhos());
        tutorExistente.setCep(tutorAtualizado.getCep());
        tutorExistente.setMunicipio(tutorAtualizado.getMunicipio());
        tutorExistente.setUf(tutorAtualizado.getUf());
        tutorExistente.setRua(tutorAtualizado.getRua());
        tutorExistente.setNumero(tutorAtualizado.getNumero());
        tutorExistente.setBairro(tutorAtualizado.getBairro());
        tutorExistente.setTelefone(tutorAtualizado.getTelefone());
        tutorExistente.setTipoResidencia(tutorAtualizado.getTipoResidencia());
        tutorExistente.setSituacaoImovel(tutorAtualizado.getSituacaoImovel());
        tutorExistente.setValorAluguel(tutorAtualizado.getValorAluguel());
        // Não alteramos o status para manter o fluxo ("Em análise" ou conforme lógica futura)

        // Atualização dos dados socioeconômicos
        DadosSocioeconomicosModel dadosAtualizados = tutorAtualizado.getDadosSocioeconomicos();
        DadosSocioeconomicosModel dadosExistentes = tutorExistente.getDadosSocioeconomicos();

        if (dadosAtualizados != null && dadosExistentes != null) {
            dadosExistentes.setSituacaoMoradia(dadosAtualizados.getSituacaoMoradia());
            dadosExistentes.setMoradiaColetivaEspecificacao(dadosAtualizados.getMoradiaColetivaEspecificacao());
            dadosExistentes.setValorAluguel(dadosAtualizados.getValorAluguel());
            dadosExistentes.setOutrosEspecificacao(dadosAtualizados.getOutrosEspecificacao());

            dadosExistentes.setLocalTrabalho(dadosAtualizados.getLocalTrabalho());
            dadosExistentes.setValorRemuneracao(dadosAtualizados.getValorRemuneracao());
            dadosExistentes.setTemOutrasFontesDeRenda(dadosAtualizados.getTemOutrasFontesDeRenda());
            dadosExistentes.setValorOutrasFontesDeRenda(dadosAtualizados.getValorOutrasFontesDeRenda());

            dadosExistentes.setTemContaBancaria(dadosAtualizados.getTemContaBancaria());
            dadosExistentes.setNomeBanco(dadosAtualizados.getNomeBanco());

            dadosExistentes.setLocalTrabalhoPai(dadosAtualizados.getLocalTrabalhoPai());
            dadosExistentes.setLocalTrabalhoMae(dadosAtualizados.getLocalTrabalhoMae());
            dadosExistentes.setLocalTrabalhoConjuge(dadosAtualizados.getLocalTrabalhoConjuge());
            dadosExistentes.setLocalTrabalhoFilhos(dadosAtualizados.getLocalTrabalhoFilhos());

            dadosExistentes.setRendaPai(dadosAtualizados.getRendaPai());
            dadosExistentes.setRendaMae(dadosAtualizados.getRendaMae());
            dadosExistentes.setRendaConjuge(dadosAtualizados.getRendaConjuge());
            dadosExistentes.setValorTotalRemuneracao(dadosAtualizados.getValorTotalRemuneracao());

            dadosExistentes.setNumeroPessoasGrupoFamiliar(dadosAtualizados.getNumeroPessoasGrupoFamiliar());
            dadosExistentes.setProgramaSocial(dadosAtualizados.getProgramaSocial());
            dadosExistentes.setOutroProgramaSocial(dadosAtualizados.getOutroProgramaSocial());
            dadosExistentes.setBensFamiliares(dadosAtualizados.getBensFamiliares());
        }

        tutorRepository.save(tutorExistente);
    }
    public void atualizarStatus(Long id, String novoStatus) {
        TutorModel tutor = tutorRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Tutor não encontrado"));

        tutor.setStatus(novoStatus);
        tutorRepository.save(tutor);
    }

}
