import { useState } from "react";
import type { PasswordResponse } from "../services/api";
import { StrengthMeter } from "./StrengthMeter";

interface Props { result: PasswordResponse }

export function PasswordResult({ result }: Props) {
    const [copied, setCopied] = useState<number | null>(null);

    async function copy(text: string, index: number) {
        await navigator.clipboard.writeText(text);
        setCopied(index);
        setTimeout(() => setCopied(null), 2000);
    }

    return (
        <div className="result-card">
            <p className="result-meta">{result.passwords.length} senha(s) · {result.length} caracteres</p>

            {result.passwords.map((password, i) => (
                <div key={i} className="result-item">
                    <code className="result-password">{password}</code>

                <StrengthMeter password={password} />

                <button
                    className={`result-copy ${copied === i ? "result-copy--done" : ""}`}
                >
                    {copied === i ? "Copiado" : "Copiar"}
                </button>
                </div>
            ))}
        </div>
    );
}