import { useState } from "react";
import type { PasswordResponse } from "../services/api";

interface Props { result: PasswordResponse }

export function PasswordResult({ result }: Props) {
    const [copied, setCopied] = useState<number | null>(null);

    async function copy(text: string, index: number) {
        await navigator.clipboard.writeText(text);
        setCopied(index);
        setTimeout(() => setCopied(null), 2000);
    }

    return ();
}