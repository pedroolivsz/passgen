import { useState } from "react";
import { generatePasswords, type PasswordRequest, type PasswordResponse } from "./services/api";

export default function App() {
    const [result, setResult] = useState<PasswordResponse | null>(null);
    const [loading, setLoading] = useState(false);
    const [error, setError] = useState<string | null>(null);

    async function handleGenerate(request: PasswordRequest) {
        setLoading(true);
        setError(null);

        try {
            const data = await generatePasswords(request);
            setResult(data);
        } catch (e: unknown) {
            setError(e instanceof Error ? e.message : "Erro desconhecido");
        } finally {
            setLoading(false);
        }
    }
}