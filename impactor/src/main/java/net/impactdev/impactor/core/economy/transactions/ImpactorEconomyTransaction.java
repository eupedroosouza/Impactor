/*
 * This file is part of Impactor, licensed under the MIT License (MIT).
 *
 * Copyright (c) 2018-2022 NickImpact
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 *
 */

package net.impactdev.impactor.core.economy.transactions;

import net.impactdev.impactor.api.economy.accounts.Account;
import net.impactdev.impactor.api.economy.currency.Currency;
import net.impactdev.impactor.api.economy.transactions.details.EconomyResultType;
import net.impactdev.impactor.api.economy.transactions.EconomyTransaction;
import net.impactdev.impactor.api.economy.transactions.details.EconomyTransactionType;
import net.impactdev.impactor.api.utility.builders.Builder;
import net.kyori.adventure.text.Component;
import org.jetbrains.annotations.Nullable;

import java.math.BigDecimal;
import java.util.function.Supplier;

public record ImpactorEconomyTransaction(
        Account account,
        Currency currency,
        BigDecimal amount,
        EconomyTransactionType type,
        EconomyResultType result,
        Supplier<Component> message
) implements EconomyTransaction {

    public static TransactionBuilder builder() {
        return new TransactionBuilder();
    }

    @Override
    public @Nullable Supplier<Component> message() {
        return this.message;
    }

    public static class TransactionBuilder implements Builder<ImpactorEconomyTransaction> {

        private Account account;
        private Currency currency;
        private BigDecimal amount;
        private EconomyTransactionType type;
        private EconomyResultType result;
        private Supplier<Component> message;

        public TransactionBuilder account(Account account) {
            this.account = account;
            return this;
        }

        public TransactionBuilder currency(Currency currency) {
            this.currency = currency;
            return this;
        }

        public TransactionBuilder amount(BigDecimal amount) {
            this.amount = amount;
            return this;
        }

        public TransactionBuilder type(EconomyTransactionType type) {
            this.type = type;
            return this;
        }

        public TransactionBuilder result(EconomyResultType result) {
            this.result = result;
            return this;
        }

        public TransactionBuilder message(Supplier<Component> message) {
            this.message = message;
            return this;
        }

        @Override
        public ImpactorEconomyTransaction build() {
            return new ImpactorEconomyTransaction(this.account,
                    this.currency,
                    this.amount,
                    this.type,
                    this.result,
                    this.message);
        }
    }
}
