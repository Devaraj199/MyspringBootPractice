package com.myflowdb.service;

import com.myflowdb.entity.UserTag;
import com.myflowdb.repository.TagRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TagService {

    @Autowired
    private TagRepository tagRepository;

    public UserTag createTag(UserTag userTag){
        return tagRepository.save(userTag);
    }

    public Optional<UserTag> findOneByTagNameAndTagValue(String tagName, String tagValue){
        return tagRepository.findOneByTagNameAndTagValue(tagName,tagValue);
    }

    public List<String> getAllFlowTagNames() {
        return tagRepository.getAllFlowTagNames();
    }

    public List<String> findTagValuesForFlowByTagName(String tagName){
        return tagRepository.findTagValuesForFlowByTagName(tagName);
    }
}
