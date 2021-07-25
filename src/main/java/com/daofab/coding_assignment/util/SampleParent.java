package com.daofab.coding_assignment.util;

import com.daofab.coding_assignment.model.Parent;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class SampleParent {
    private List<Parent> data;
}
