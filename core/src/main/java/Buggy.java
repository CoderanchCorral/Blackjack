/*
 * Copyright (C) 2018 Coderanch.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

import java.util.concurrent.atomic.*;

public class Buggy {

    public boolean equals(Buggy other) {
        var var  = new AtomicInteger(3);
        var var2 = 4;

        return var.equals(var2 =+ 5);
    }
}
