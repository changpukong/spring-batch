package yfu.practice.springbatch.completionpolicy;

import yfu.practice.springbatch.dto.YfuCardDto;

public class GroupItemCompletionPolicy extends AbstractCompletionPolicy<YfuCardDto> {

    @Override
    public boolean isComplete(YfuCardDto item, YfuCardDto nextItem) {
        return nextItem != null && !item.getType().equals(nextItem.getType());
    }
    
}